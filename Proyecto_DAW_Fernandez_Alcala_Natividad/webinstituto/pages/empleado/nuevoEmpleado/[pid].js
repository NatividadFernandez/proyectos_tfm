import React, { useState } from 'react';
import Layout from "../../../components/Layout";
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { useMutation, gql } from '@apollo/client';
import { useRouter } from "next/router";
import Image from 'next/image';
import Swal from 'sweetalert2';
import Router from "next/router";
import { ApolloError } from '@apollo/client';
import { Formik } from "formik";

const NUEVO_EMPLEADO = gql`
    mutation Mutation($input: EmpleadoInput) {
        nuevoEmpleado(input: $input) {
            nombre
            apellido
            nif
            telefono
            email
            empresa
        }
    }    
`;

export default function NuevoEmpleado() {

    // obtener el ID actual
    const router = useRouter();
    const { pid } = router.query

    //console.log(pid);

    // Mensaje de alerta
    const [mensaje, guardarMensaje] = useState(null);

    const [nuevoEmpleado] = useMutation(NUEVO_EMPLEADO /*{
        update(cache, { data: { nuevoEmpleado } }) {
            // obtener el objeto de cache que deseamos actualizar
            const { obtenerEmpleados } = cache.readQuery({ query: OBTENER_EMPLEADOS_EMPRESA,
                query: OBTENER_EMPLEADOS_EMPRESA
            });

            // Reescribimos el cache (el caceh nunca se debe modificar)
            cache.writeQuery({
                query: OBTENER_EMPLEADOS_EMPRESA,
                data: {
                    obtenerEmpleados: [...obtenerEmpleados, nuevoEmpleado]
                }               
            })
        }
    }*/)

    //const [nuevoEmpleado] = useMutation(NUEVO_EMPLEADO);

    const schemaValidacion = Yup.object({
        nombre: Yup.string()
            .required('El nombre del empleado es obligatorio'),
        apellido: Yup.string()
            .required('Los apellidos del empleado son obligatorios'),
        nif: Yup.string()
            .matches(/^\d{8}[a-zA-Z]$/, 'El formato del dni es incorrecto')
            .required('El nif es obligatorio'),
        telefono: Yup.string()
            .matches(/^[0-9]{9}$/, 'El formato del teléfono es incorrecto')
            .required('El teléfono es obligatorio'),
        email: Yup.string()
            .email('El email no es válido')
            .required('El email no puede ir vacío')
    });

    if (!pid) return 'Cargando...';

    const addEmpleado = async valores => {
        console.log(valores);
        const { nif, nombre, apellido, email, telefono, empresa } = valores;
        try {
            const { data } = await nuevoEmpleado({
                variables: {
                    input: {
                        nif,
                        nombre,
                        apellido,
                        email,
                        telefono,
                        empresa
                    }
                }
            });

            Swal.fire(
                'Añadido',
                'El empleado se añadió correctamente',
                'success'
            ); 

            Router.push({
                pathname: "../../empresa/informacionEmpresa/[id]",
                query: { id: pid }
            }) // Redirrecionar hacia ciclos
        } catch (error) {
            guardarMensaje(error.message);

            setTimeout(() => {
                guardarMensaje(null);
            }, 3000);
            //console.log(error);
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
                <div className="">
                    <div>
                        <div className="text-center">
                            {mensaje && mostrarMensaje()}
                            <Image
                                src="/empleados.png"
                                alt="Landscape picture"
                                width={130}
                                height={130}
                                priority
                            />
                        </div>
                        <div className="container mx-auto xl:px-32">
                            <div className="grid lg:grid-cols-1 gap-12">
                                <div className="mb-10 lg:mb-0">
                                    <div className="block rounded-lg shadow-lg bg-white px-6 py-12 md:px-12">
                                        
                                        <Formik validationSchema={schemaValidacion} enableReinitialize
                                            initialValues={{
                                                nombre: '',
                                                apellido: '',
                                                nif: '',
                                                telefono: '',
                                                email: '',
                                                empresa: `${pid}`
                                            }}

                                            onSubmit={(valores) => {
                                                addEmpleado(valores);
                                            }}
                                        >
                                            {props => {

                                                return (

                                                    <form onSubmit={props.handleSubmit}>                                                      
                                                        <div className="grid xl:grid-cols-2 xl:gap-6">
                                                            <div className="relative z-0 w-full mb-6 group">
                                                                <label htmlFor="nombre" className="font-medium  text-sm text-gray-500 ">Nombre</label>
                                                                <input type="text" name="nombre" id="nombre" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer capitalize"
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
                                                                <label htmlFor="apellido" className="font-medium  text-sm text-gray-500">Apellidos</label>
                                                                <input type="text" name="apellido" id="apellido" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer capitalize"
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

                                                        <div className="relative z-0 w-full mb-6 group">
                                                            <label htmlFor="email" className="font-medium  text-sm text-gray-500 dark:text-gray-400 ">Email</label>
                                                            <input type="email" name="email" id="email" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
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

                                                        <div className="grid xl:grid-cols-2 xl:gap-6">
                                                            <div className="relative z-0 w-full mb-6 group">
                                                                <label htmlFor="nif" className="font-medium  text-sm text-gray-500 dark:text-gray-400">DNI</label>
                                                                <input type="text" name="nif" id="nif" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer uppercase"
                                                                    onChange={props.handleChange}
                                                                    onBlur={props.handleBlur}
                                                                    value={props.values.nif} />
                                                                {props.touched.nif && props.errors.nif ? (
                                                                    <div className='my-2 bg-red-100 border-l-4 border-red-500 text-red-700 p-4'>
                                                                        <p className='font-bold'>Error</p>
                                                                        <p>{props.errors.nif}</p>
                                                                    </div>
                                                                ) : null}
                                                            </div>
                                                            <div className="relative z-0 w-full mb-6 group">
                                                                <label htmlFor="telefono" className="font-medium  text-sm text-gray-500 dark:text-gray-400 ">Teléfono</label>
                                                                <input type="number" name="telefono" id="telefono" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-blue-600 peer"
                                                                    onChange={props.handleChange}
                                                                    onBlur={props.handleBlur}
                                                                    value={props.values.telefono} />
                                                                {props.touched.telefono && props.errors.telefono ? (
                                                                    <div className='my-2 bg-red-100 border-l-4 border-red-500 text-red-700 p-4'>
                                                                        <p className='font-bold'>Error</p>
                                                                        <p>{props.errors.telefono}</p>
                                                                    </div>
                                                                ) : null}
                                                            </div>
                                                        </div>
                                                        <div className='flex justify-center'>
                                                            <input type="submit" data-mdb-ripple="true" data-mdb-ripple-color="light" className="inline-block px-6 py-2.5 mb-2 mt-4 bg-blue-600 text-white font-medium text-xs leading-tight uppercase rounded shadow-md hover:bg-blue-700 hover:shadow-lg focus:bg-blue-700 focus:shadow-lg focus:outline-none focus:ring-0 active:bg-blue-800 active:shadow-lg transition duration-150 ease-in-out"
                                                                value="Añadir Nuevo Empleado" />
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
                </div>
            </Layout>
        </>
    )

}