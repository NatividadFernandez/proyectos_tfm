import React from "react";
import { useRouter } from "next/router";
import Layout from "../../../components/Layout";
import { useQuery, useMutation, gql } from "@apollo/client";
import { Formik } from "formik";
import * as Yup from 'yup';
import Swal from 'sweetalert2';
import Image from 'next/image';

const OBTENER_CICLO = gql`
    query ObtenerCiclo($id: ID!) {
        obtenerCiclo(id: $id) {
            nombreCorto
            nombreLargo
        }
    }
`;

const ACTUALIZAR_CICLO = gql`
    mutation ActualizarCiclo($input: CicloInput, $id: ID!) {
        actualizarCiclo(input: $input, id: $id) {
          nombreCorto
          nombreLargo
        }
      }
`;

export default function EditarCiclo() {

    // obtener el ID actual
    const router = useRouter();
    //const { query: { pid } } = router;
    const { pid } = router.query
    //console.log(pid);

    // Consultar para obtener el ciclo
    const { data, loading, error } = useQuery(OBTENER_CICLO, {
        variables: {
            id: pid
        }
    });

    // Actualizar el ciclo
    const [actualizarCiclo] = useMutation(ACTUALIZAR_CICLO);


    // Schema de validacion
    const schemaValidacion = Yup.object({
        nombreCorto: Yup.string()
            .required('El nombre abreviado del ciclo es obligatorio'),
        nombreLargo: Yup.string()
            .required('El nombre completo del ciclo es obligatorio')
    });


    if (loading) return 'Cargando...';

    if (!pid) return 'Cargando...';

    const { obtenerCiclo } = data;

    // Modifica el ciclo de la BD
    const actualizarInfoCiclo = async valores => {
        const { nombreCorto, nombreLargo } = valores;

        try {
            const { data } = await actualizarCiclo({
                variables: {
                    id: pid,
                    input: {
                        nombreCorto,
                        nombreLargo
                    }
                }
            });

            // Mostrar alerta
            Swal.fire(
                'Actualizado',
                'El ciclo se actualizó correctamente',
                'success'
            );

            // Redireccionar
            router.push('/ciclos/ciclos');

        } catch (error) {
            console.log(error);
        }
    }


    return (
        <>
            <Layout>           

                <div className="">
                    <div>
                        <div className="text-center">
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
                                        <Formik validationSchema={schemaValidacion} enableReinitialize
                                            initialValues={obtenerCiclo}
                                            onSubmit={(valores) => {
                                                actualizarInfoCiclo(valores);
                                            }}
                                        >

                                            {props => {

                                                return (
                                                    <form onSubmit={props.handleSubmit}>
                                                        <div className="relative z-0 w-full mb-6 group">
                                                            <label htmlFor="nombreCorto" className="font-medium  text-sm text-gray-500 ">Abreviatura ciclo</label>
                                                            <input type="nombreCorto" name="nombreCorto" id="nombreCorto"  className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer uppercase"
                                                                placeholder=" "
                                                                onChange={props.handleChange}
                                                                onBlur={props.handleBlur}
                                                                value={props.values.nombreCorto} />
                                                            {props.touched.nombreCorto && props.errors.nombreCorto ? (
                                                                <div className='my-2 bg-red-100 border-l-4 border-red-500 text-red-700 p-4'>
                                                                    <p className='font-bold'>Error</p>
                                                                    <p>{props.errors.nombreCorto}</p>
                                                                </div>
                                                            ) : null}
                                                        </div>
                                                        <div className="relative z-0 w-full mb-6 group">
                                                            <label htmlFor="nombreLargo" className="font-medium  text-sm text-gray-500 ">Nombre completo del ciclo</label>
                                                            <input type="nombreLargo" name="nombreLargo" id="nombreLargo"  className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer capitalize"
                                                                placeholder=" "
                                                                onChange={props.handleChange}
                                                                onBlur={props.handleBlur}
                                                                value={props.values.nombreLargo} />
                                                            {props.touched.nombreLargo && props.errors.nombreLargo ? (
                                                                <div className='my-2 bg-red-100 border-l-4 border-red-500 text-red-700 p-4'>
                                                                    <p className='font-bold'>Error</p>
                                                                    <p>{props.errors.nombreLargo}</p>
                                                                </div>
                                                            ) : null}
                                                        </div>
                                                        <input type="submit" data-mdb-ripple="true" data-mdb-ripple-color="light" className="inline-block px-6 py-2.5 mb-2 mt-4 w-full bg-blue-600 text-white font-medium text-xs leading-tight uppercase rounded shadow-md hover:bg-blue-700 hover:shadow-lg focus:bg-blue-700 focus:shadow-lg focus:outline-none focus:ring-0 active:bg-blue-800 active:shadow-lg transition duration-150 ease-in-out"
                                                            value="Editar ciclo" />
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
    );
}