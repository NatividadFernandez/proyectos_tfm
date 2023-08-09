import React, { useState } from "react";
import { useRouter } from "next/router";
import Layout from "../../components/Layout";
import { useQuery, useMutation, gql } from "@apollo/client";
import { Formik } from "formik";
import * as Yup from 'yup';
import Swal from 'sweetalert2';
import Image from 'next/image';
import Link from 'next/link';

const OBTENER_USUARIO = gql`
    query ObtenerUsuario {
        obtenerUsuario {
            id
            nombre
            apellido
            email
            password
        }
    }
`;

const ACTUALIZAR_USUARIO = gql`
    mutation Mutation($id: ID!, $input: UsuarioInput) {
        actualizarUsuario(id: $id, input: $input) {
            nombre
            apellido
            email
            password
        }
    }
`;

export default function ConfiguracionUsuario() {

    const router = useRouter();

    // Mensaje de alerta
    const [mensaje, guardarMensaje] = useState(null);

    const [mostrarComponente, setMostrarComponente] = useState(false);

    const [operativo, setOperativo] = useState(true);

    const { data, loading, error } = useQuery(OBTENER_USUARIO);

    // Actualizar el usuario
    const [actualizarUsuario] = useMutation(ACTUALIZAR_USUARIO);

    // Proteger que no accedamos a data antes de tener resultados
    if (loading || !data) return 'Cargando...';

    const { obtenerUsuario } = data;

    // Schema de validacion
    const schemaValidacion = Yup.object({
        nombre: Yup.string()
            .required('El nombre es obligatorio'),
        apellido: Yup.string()
            .required('El apellido es obligatorio'),
        email: Yup.string()
            .email('El email no es válido')
            .required('El email es obligatorio'),
        password: Yup.string()
            .min(6, 'La contraseña debe de ser de al menos 6 caracteres').nullable(),
        confirmarPassword: Yup.string()
            .oneOf([Yup.ref('password'), null], 'Las contraseñas deben coincidir')
    });

    // Modifica el usuario de la BD
    const actualizarInfoUsuario = async valores => {
        const { nombre, apellido, email, password } = valores;
        console.log(valores);
        console.log(obtenerUsuario.id);
        console.log(password == '');
        try {

            const { data } = await actualizarUsuario({
                variables: {
                    id: obtenerUsuario.id,
                    input: {
                        nombre,
                        apellido,
                        email,
                        password
                    }
                }
            });

            console.log(data);

            // Usuario creado correctamente
            //guardarMensaje(`Se creó correctamente el Usuario: ${data.nuevoUsuario.nombre}`);

            // Mostrar alerta
            Swal.fire(
                'Actualizado',
                'Información actualizada correctamente',
                'success'
            );

            setOperativo(!operativo)
            setMostrarComponente(!mostrarComponente)

        } catch (error) {
            guardarMensaje(error.message);
            setTimeout(() => {
                guardarMensaje(null);
            }, 3000)
        }
    }

    const mostrarMensaje = () => {
        return (
            <div className='bg-white py-2 px-3 w-full my-3 max-w-sm text-center mx-auto'>
                <p>{mensaje}</p>
            </div>
        )
    }

    return (
        <>
            <Layout>
                <div>
                    <div className="text-center">
                    {mensaje && mostrarMensaje()}
                        <Image
                            src="/usuario.png"
                            alt="Landscape picture"
                            width={130}
                            height={130}
                            priority
                        />
                    </div>
                    <div className="container mx-auto xl:px-32">

                        <div className="grid lg:grid-cols-1 gap-12">                        
                            <div className="mb-12 lg:mb-0">
                                <div className="block rounded-lg shadow-lg bg-white px-6 py-10 md:px-12">
                                    <div className='relative px-4'>
                                        <button type="button" className="absolute top-0 right-0" title="Editar" onClick={/*() => setMostrarComponente(!mostrarComponente*/ () => { setOperativo(!operativo); setMostrarComponente(!mostrarComponente); }}>
                                            <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6 m-2 stroke-gray-400 active:stroke-gray-900" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                                                <path strokeLinecap="round" strokeLinejoin="round" d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z" />
                                            </svg>
                                        </button>
                                    </div>
                                    <div className="p-3"></div>
                                    <Formik validationSchema={schemaValidacion} enableReinitialize
                                        initialValues={(obtenerUsuario != null) ?
                                            {
                                                nombre: obtenerUsuario.nombre,
                                                apellido: obtenerUsuario.apellido,
                                                email: obtenerUsuario.email,
                                                password: '',
                                                confirmarPassword: ''
                                            } : ''}
                                        onSubmit={(valores) => {
                                            actualizarInfoUsuario(valores);
                                        }}
                                    >

                                        {props => {

                                            return (
                                                <form onSubmit={props.handleSubmit}>
                                                    {/*} <div className={mostrarComponente ? "show-element" : null}>
                                                            {mostrarComponente && */}
                                                    <div className="grid xl:grid-cols-2 xl:gap-6">
                                                        <div className="relative z-0 w-full mb-6 group">
                                                            <label htmlFor="nombre" className="font-medium  text-sm text-gray-500 dark:text-gray-400 ">Nombre</label>
                                                            <input type="text" name="nombre" id="nombre" disabled={operativo ? 'disabled' : null} className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
                                                                onChange={props.handleChange}
                                                                onBlur={props.handleBlur}
                                                                value={props.values.nombre} />
                                                            {props.touched.nombre && props.errors.nombre ? (
                                                                <div className='my-2 bg-red-100 border-l-4 border-red-500 text-red-700 p-4'>
                                                                    <p className='font-bold'>Error</p>
                                                                    <p>{props.errors.nombre}</p>
                                                                </div>
                                                            ) : null}
                                                        </div>
                                                        <div className="relative z-0 w-full mb-6 group">
                                                            <label htmlFor="apellido" className="font-medium  text-sm text-gray-500 dark:text-gray-400">Apellidos</label>
                                                            <input type="text" name="apellido" id="apellido" disabled={operativo ? 'disabled' : null} className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
                                                                onChange={props.handleChange}
                                                                onBlur={props.handleBlur}
                                                                value={props.values.apellido} />
                                                            {props.touched.apellido && props.errors.apellido ? (
                                                                <div className='my-2 bg-red-100 border-l-4 border-red-500 text-red-700 p-4'>
                                                                    <p className='font-bold'>Error</p>
                                                                    <p>{props.errors.apellido}</p>
                                                                </div>
                                                            ) : null}

                                                        </div>
                                                    </div>
                                                    {/*}  }
                                                        </div>*/}
                                                    {/*<div className="grid xl:grid-cols-2 xl:gap-6">*/}
                                                    <div className="relative z-0 w-full mb-6 group">
                                                        <label htmlFor="email" className="font-medium  text-sm text-gray-500 dark:text-gray-400 ">Email</label>
                                                        <input type="email" name="email" id="email" disabled={operativo ? 'disabled' : null} className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
                                                            onChange={props.handleChange}
                                                            onBlur={props.handleBlur}
                                                            value={props.values.email} />
                                                        {props.touched.email && props.errors.email ? (
                                                            <div className='my-2 bg-red-100 border-l-4 border-red-500 text-red-700 p-4'>
                                                                <p className='font-bold'>Error</p>
                                                                <p>{props.errors.email}</p>
                                                            </div>
                                                        ) : null}
                                                    </div>
                                                    <div className={mostrarComponente ? "show-element" : null}>
                                                        {mostrarComponente &&
                                                            <div>
                                                                <div className="grid xl:grid-cols-2 xl:gap-6">
                                                                    <div className="relative z-0 w-full mb-6 group">
                                                                        <label htmlFor="password" className="font-medium  text-sm text-gray-500 dark:text-gray-400">Nueva Contraseña</label>
                                                                        <input type="password" name="password" id="password" disabled={operativo ? 'disabled' : null} className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
                                                                            onChange={props.handleChange}
                                                                            onBlur={props.handleBlur}
                                                                            value={props.values.password} />
                                                                        {props.touched.password && props.errors.password ? (
                                                                            <div className='my-2 bg-red-100 border-l-4 border-red-500 text-red-700 p-4'>
                                                                                <p className='font-bold'>Error</p>
                                                                                <p>{props.errors.password}</p>
                                                                            </div>
                                                                        ) : null}

                                                                    </div>
                                                                    <div className="relative z-0 w-full mb-6 group">
                                                                        <label htmlFor="password" className="font-medium  text-sm text-gray-500 dark:text-gray-400">Confirmar contraseña</label>
                                                                        <input type="password" name="confirmarPassword" id="confirmarPassword" disabled={operativo ? 'disabled' : null} className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
                                                                            onChange={props.handleChange}
                                                                            onBlur={props.handleBlur}
                                                                            value={props.values.confirmarPassword} />
                                                                        {props.touched.confirmarPassword && props.errors.confirmarPassword ? (
                                                                            <div className='my-2 bg-red-100 border-l-4 border-red-500 text-red-700 p-4'>
                                                                                <p className='font-bold'>Error</p>
                                                                                <p>{props.errors.confirmarPassword}</p>
                                                                            </div>
                                                                        ) : null}
                                                                    </div>
                                                                </div>
                                                                <input type="submit" data-mdb-ripple="true" data-mdb-ripple-color="light" disabled={operativo ? 'disabled' : null} className="inline-block px-6 py-2.5 mb-2 mt-4 w-full bg-blue-600 text-white font-medium text-xs leading-tight uppercase rounded shadow-md hover:bg-blue-700 hover:shadow-lg focus:bg-blue-700 focus:shadow-lg focus:outline-none focus:ring-0 active:bg-blue-800 active:shadow-lg transition duration-150 ease-in-out"
                                                                    value={'Actualizar datos'} />
                                                            </div>
                                                        }
                                                    </div>
                                                </form>
                                            )
                                        }}
                                    </Formik>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>                

            </Layout>

        </>
    );
}