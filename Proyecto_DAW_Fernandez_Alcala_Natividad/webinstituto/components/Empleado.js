import React, { useState } from 'react';
import Swal from 'sweetalert2';
import { useQuery, useMutation, gql } from "@apollo/client";
import Router from "next/router";
import { Formik } from "formik";
import * as Yup from 'yup';

const ELIMINAR_EMPLEADO = gql`
    mutation Mutation($id: ID!) {
        eliminarEmpleado(id: $id)
    }
`;
const OBTENER_EMPLEADOS = gql`
query Query($id: ID!) {
    obtenerEmpleadosEmpresa(id: $id) {
      id
      nombre
      apellido
      nif
      telefono
      email   
    }
  }
`;

const ACTUALIZAR_EMPLEADO = gql`
    mutation ActualizarEmpleado($id: ID!, $input: EmpleadoInput) {
        actualizarEmpleado(id: $id, input: $input) {
            nombre
            apellido
            nif
            telefono
            email
            empresa
        }
    }
`;

export default function Empleado({ empleado }) {

    // Mensaje de alerta
    const [mensaje, guardarMensaje] = useState(null);

    const { nombre, apellido, nif, telefono, email, id, empresa } = empleado;

    const [mostrarComponente, setMostrarComponente] = useState(false);

    const [operativo, setOperativo] = useState(true);

    // Consultar para obtener el empleados
    const { data, loading, error } = useQuery(OBTENER_EMPLEADOS, {
        variables: {
            id: empresa
        }
    });

     // Actualizar el usuario
     const [actualizarEmpleado] = useMutation(ACTUALIZAR_EMPLEADO);

    const [eliminarEmpleado] = useMutation(ELIMINAR_EMPLEADO, {
        update(cache) {
            // obtener una copia del objeto de cache
            const { obtenerEmpleadosEmpresa } = cache.readQuery({
                query: OBTENER_EMPLEADOS,
                variables: {
                    id: empresa
                }
            });

            // Reescribir el cache
            cache.writeQuery({
                query: OBTENER_EMPLEADOS,
                data: {
                    obtenerEmpleadosEmpresa: obtenerEmpleadosEmpresa.filter(empleadoActual => empleadoActual.id !== id)
                },
                variables: {
                    id: empresa
                }
            });
        }
    });

    // Schema de validacion
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

    if (loading) return 'Cargando...';

    // Mutation para eliminar empleado
    const confirmarEliminarEmpleado = () => {
        Swal.fire({
            title: '¿Deseas eliminar este empleado?',
            text: "¡Esta acción no se puede deshacer!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '¡Si, Eliminar!',
            cancelButtonText: 'No, Cancelar'
        }).then(async (result) => {
            if (result.isConfirmed) {
                //console.log('Eliminando...',id);               
                try {
                    // Eliminar por ID
                    const { data } = await eliminarEmpleado({
                        variables: {
                            id
                        }
                    })
                    //console.log(data);

                    Swal.fire(
                        'Eliminado',
                        data.eliminarEmpleado, // Al devolvernos un mensjae, los mostrams en el alert
                        'success'
                    )
                } catch (error) {
                    console.log(error);
                }
            }
        })

    }

    // Modifica el empleado de la BD
    const actualizarInfoEmpleado = async valores => {
        const { nombre, apellido, nif, telefono, email } = valores;
        console.log(valores);;
        try {
            const { data } = await actualizarEmpleado({
                variables: {
                    id: id,
                    input: {
                        nombre,
                        apellido,
                        nif,
                        telefono,
                        email,
                        empresa: empresa
                    }
                }
            });

            //console.log(data);

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
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: mensaje,
            footer: '<a href="">Vuelve a comprobar los datos introducidos</a>'
          }) 
    }

    return (

        <div className="py-3">
            {mensaje && mostrarMensaje()}
            <div className=" w-full lg:max-w-full lg:flex ">
                <div className="w-full bg-gray-100 rounded shadow-xl p-4 flex flex-col justify-between leading-normal">
                    <div className="">
                        <Formik validationSchema={schemaValidacion} enableReinitialize
                            initialValues={empleado}
                            onSubmit={(valores) => {
                                actualizarInfoEmpleado(valores);
                            }}
                        >

                            {props => {

                                return (
                                    <form onSubmit={props.handleSubmit}>
                                        <div className='flex justify-between'>
                                            <div>
                                                <span className="text-sm text-gray-600 flex items-center">
                                                    <svg xmlns="http://www.w3.org/2000/svg" className="fill-current h-6 w-6 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                                                        <path strokeLinecap="round" strokeLinejoin="round" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                                                    </svg>
                                                    <div className={mostrarComponente ? "show-element" : null}>
                                                        {mostrarComponente ||
                                                            <span className='text-xl'>{nombre} {apellido}</span>
                                                        }
                                                    </div>
                                                    <div className={mostrarComponente ? "show-element" : null}>
                                                        {mostrarComponente &&
                                                            <div className="grid xl:grid-cols-2 xl:gap-6">
                                                                <div className="">
                                                                    <label className='font-medium mr-2'>Nombre</label>
                                                                    <input type="text" name="nombre" id="nombre" disabled={operativo ? 'disabled' : null} className="w-full capitalize"
                                                                        onChange={props.handleChange}
                                                                        onBlur={props.handleBlur}
                                                                        value={props.values.nombre}
                                                                    />
                                                                    {props.touched.nombre && props.errors.nombre ? (
                                                                        <div className='my-2 text-red-700'>
                                                                            <p>{props.errors.nombre}</p>
                                                                        </div>
                                                                    ) : null}
                                                                </div>
                                                                <div className="">
                                                                    <label className='font-medium mr-2'>Apellidos</label>
                                                                    <input type="text" name="apellido" id="apellido" disabled={operativo ? 'disabled' : null} className="w-full capitalize"
                                                                        onChange={props.handleChange}
                                                                        onBlur={props.handleBlur}
                                                                        value={props.values.apellido}
                                                                    />
                                                                    {props.touched.apellido && props.errors.apellido ? (
                                                                        <div className='my-2 text-red-700'>
                                                                            <p>{props.errors.apellido}</p>
                                                                        </div>
                                                                    ) : null}
                                                                </div>
                                                            </div>
                                                        }
                                                    </div>

                                                </span>
                                            </div>
                                            <div className='flex justify-around px-4'>
                                                <button type="button" className="inline-block mr-4" onClick={() => { setOperativo(!operativo); setMostrarComponente(!mostrarComponente); }}>
                                                    
                                                    <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 stroke-green-400 active:stroke-green-900" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                                                        <path strokeLinecap="round" strokeLinejoin="round" d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z" />
                                                    </svg>
                                                </button>
                                                <button type="button" className=" inline-block" onClick={() => confirmarEliminarEmpleado()}>
                                                    <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 stroke-red-600 active:stroke-red-900" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                                                        <path strokeLinecap="round" strokeLinejoin="round" d="M13 7a4 4 0 11-8 0 4 4 0 018 0zM9 14a6 6 0 00-6 6v1h12v-1a6 6 0 00-6-6zM21 12h-6" />
                                                    </svg>
                                                </button>
                                            </div>
                                        </div>
                                        <div className="relative z-0 w-full mb-6 mt-1 group divide-y-2 divide-gray-300">
                                            <div></div>
                                            <div></div>
                                        </div>
                                        <div className="grid xl:grid-cols-3 xl:gap-6">
                                            <div className="relative z-0 w-full mb-4 group">
                                                <span className="text-md text-gray-600 flex items-center font-medium">
                                                    <div>
                                                        <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                                                            <path strokeLinecap="round" strokeLinejoin="round" d="M10 6H5a2 2 0 00-2 2v9a2 2 0 002 2h14a2 2 0 002-2V8a2 2 0 00-2-2h-5m-4 0V5a2 2 0 114 0v1m-4 0a2 2 0 104 0m-5 8a2 2 0 100-4 2 2 0 000 4zm0 0c1.306 0 2.417.835 2.83 2M9 14a3.001 3.001 0 00-2.83 2M15 11h3m-3 4h2" />
                                                        </svg>
                                                    </div>
                                                    <input type="text" name="nif" id="nif" disabled={operativo ? 'disabled' : null} className="w-full capitalize"
                                                        onChange={props.handleChange}
                                                        onBlur={props.handleBlur}
                                                        value={props.values.nif}
                                                    />                                                    
                                                </span>
                                                {props.touched.nif && props.errors.nif ? (
                                                        <div className='my-2 text-red-700 text-center text-sm'>
                                                            <p>{props.errors.nif}</p>
                                                        </div>
                                                    ) : null}
                                            </div>
                                            <div className="relative z-0 w-full mb-4 group">
                                                <span className="text-md text-gray-600 flex items-center font-medium">
                                                    <div>
                                                        <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                                                            <path strokeLinecap="round" strokeLinejoin="round" d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z" />
                                                        </svg>
                                                    </div>
                                                    <input type="number" name="telefono" id="telefono" disabled={operativo ? 'disabled' : null} className="w-full"
                                                        onChange={props.handleChange}
                                                        onBlur={props.handleBlur}
                                                        value={props.values.telefono}
                                                    />                                                    
                                                </span>
                                                {props.touched.telefono && props.errors.telefono ? (
                                                        <div className='my-2 text-red-700 text-center text-sm'>
                                                            <p>{props.errors.telefono}</p>
                                                        </div>
                                                    ) : null}
                                            </div>
                                            <div className="relative z-0 w-full mb-4 group">
                                                <span className="text-md text-gray-600 flex items-center font-medium">
                                                    <div>
                                                        <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                                                            <path strokeLinecap="round" strokeLinejoin="round" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
                                                        </svg>
                                                    </div>
                                                    <input type="email" name="email" id="email" disabled={operativo ? 'disabled' : null} className="w-full"
                                                        onChange={props.handleChange}
                                                        onBlur={props.handleBlur}
                                                        value={props.values.email}
                                                    />                                                    
                                                </span>
                                                {props.touched.email && props.errors.email ? (
                                                        <div className='my-2 text-red-700 text-center text-sm'>
                                                            <p>{props.errors.email}</p>
                                                        </div>
                                                    ) : null}
                                            </div>
                                        </div>
                                        <div className={mostrarComponente ? "show-element" : null}>
                                            {mostrarComponente &&
                                                <div className='flex justify-center '>
                                                    <div>
                                                        <input type="submit" data-mdb-ripple="true" data-mdb-ripple-color="light" disabled={operativo ? 'disabled' : null} className="inline-block px-6 py-2.5 mb-2 mt-4 bg-blue-600 text-white font-medium text-xs leading-tight uppercase rounded shadow-md hover:bg-blue-700 hover:shadow-lg focus:bg-blue-700 focus:shadow-lg focus:outline-none focus:ring-0 active:bg-blue-800 active:shadow-lg transition duration-150 ease-in-out"
                                                            value={'Actualizar datos'} />
                                                    </div>
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

    );
}