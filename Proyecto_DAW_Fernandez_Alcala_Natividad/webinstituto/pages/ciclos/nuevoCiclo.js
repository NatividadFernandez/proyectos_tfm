import React, { useState } from 'react';
import Layout from "../../components/Layout";
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { useMutation, gql } from '@apollo/client';
import { useRouter } from "next/router";
import Image from 'next/image';
import Swal from 'sweetalert2';

const NUEVO_CICLO = gql`
    mutation Mutation($input: CicloInput) {
        nuevoCiclo(input: $input) {    
            nombreCorto
            nombreLargo
            creado
        }
    }    
`;

const OBTENER_CICLOS = gql`
  query Query {
      obtenerCiclos {
          nombreCorto
          nombreLargo
      }
  }
`;

export default function NuevoCiclo() {

    const router = useRouter();

    // Mensaje de alerta
    const [mensaje, guardarMensaje] = useState(null);

    const [nuevoCiclo] = useMutation(NUEVO_CICLO, {
        update(cache, { data: { nuevoCiclo } }) {
            // obtener el objeto de cache que deseamos actualizar
            const { obtenerCiclos } = cache.readQuery({ query: OBTENER_CICLOS });

            // Reescribimos el cache (el cache nunca se debe modificar)
            cache.writeQuery({
                query: OBTENER_CICLOS,
                data: {
                    obtenerCiclos: [...obtenerCiclos, nuevoCiclo]
                }
            });
        }
    })

    const formik = useFormik({
        initialValues: {
            nombreCorto: '',
            nombreLargo: ''
        },
        validationSchema: Yup.object({
            nombreCorto: Yup.string()
                .required('El nombre abreviado del ciclo es obligatorio'),
            nombreLargo: Yup.string()
                .required('El nombre completo del ciclo es obligatorio')
        }),
        onSubmit: async valores => {
            console.log(valores);
            const { nombreCorto, nombreLargo } = valores;
            try {
                const { data } = await nuevoCiclo({
                    variables: {
                        input: {
                            nombreCorto,
                            nombreLargo
                        }
                    }
                });

                Swal.fire(
                    'Añadido',
                    'El ciclo se añadió correctamente',
                    'success'
                );
                
                router.push('/ciclos/ciclos'); // Redirrecionar hacia ciclos
            } catch (error) {
                guardarMensaje(error.message);

                setTimeout(() => {
                    guardarMensaje(null);
                }, 3000);
                //console.log(error);
            }
        }
    })

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
                <div className="pt-6">
                    <div>
                        <div className="text-center">
                        {mensaje && mostrarMensaje()}
                            <Image
                                src="/educa.png"
                                alt="Landscape picture"
                                width={130}
                                height={130}
                                priority
                            />
                        </div>
                        <div className="container mx-auto xl:px-32">
                            <div className="grid lg:grid-cols-1 gap-12">
                                <div className="mb-12 lg:mb-0">
                                    <div className="block rounded-lg shadow-lg bg-white px-6 py-12 md:px-12">

                                        <form onSubmit={formik.handleSubmit}>
                                            <div className="relative z-0 w-full mb-6 group">
                                                <label htmlFor="nombreCorto" className="font-medium  text-sm text-gray-500 ">Abreviatura ciclo</label>
                                                <input type="nombreCorto" name="nombreCorto" id="nombreCorto" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer uppercase"
                                                    placeholder="Ej: DAW"
                                                    onChange={formik.handleChange}
                                                    onBlur={formik.handleBlur}
                                                    value={formik.values.nombreCorto} />
                                                {formik.touched.nombreCorto && formik.errors.nombreCorto ? (
                                                    <div className='my-2 bg-red-100 border-l-4 border-red-500 text-red-700 p-4'>
                                                        <p className='font-bold'>Error</p>
                                                        <p>{formik.errors.nombreCorto}</p>
                                                    </div>
                                                ) : null}
                                            </div>
                                            <div className="relative z-0 w-full mb-6 group">
                                                <label htmlFor="nombreLargo" className="font-medium  text-sm text-gray-500 ">Nombre completo del ciclo</label>
                                                <input type="nombreLargo" name="nombreLargo" id="nombreLargo" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer capitalize"
                                                    placeholder="Ej: Desarrollo de Aplicaciones Web "
                                                    onChange={formik.handleChange}
                                                    onBlur={formik.handleBlur}
                                                    value={formik.values.nombreLargo} />
                                                {formik.touched.nombreLargo && formik.errors.nombreLargo ? (
                                                    <div className='my-2 bg-red-100 border-l-4 border-red-500 text-red-700 p-4'>
                                                        <p className='font-bold'>Error</p>
                                                        <p>{formik.errors.nombreLargo}</p>
                                                    </div>
                                                ) : null}
                                            </div>
                                            <input type="submit" data-mdb-ripple="true" data-mdb-ripple-color="light" className="inline-block px-6 py-2.5 mb-2 mt-4 w-full bg-blue-600 text-white font-medium text-xs leading-tight uppercase rounded shadow-md hover:bg-blue-700 hover:shadow-lg focus:bg-blue-700 focus:shadow-lg focus:outline-none focus:ring-0 active:bg-blue-800 active:shadow-lg transition duration-150 ease-in-out"
                                                value="Añadir Nuevo Ciclo" />
                                        </form>
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