import React, { useState } from "react";
import { useRouter } from "next/router";
import Layout from "../../../components/Layout";
import { useQuery, useMutation, gql } from "@apollo/client";
import { Formik } from "formik";
import * as Yup from 'yup';
import Swal from 'sweetalert2';
import Select from 'react-select';

const OBTENER_EMPRESA = gql`
    query ObtenerEmpresa($id: ID!) {
        obtenerEmpresa(id: $id) {
            id
            nombre
            cif
            representante
            nif
            telefono
            direccion
            ciclos {
                id
                nombreCorto
                nombreLargo
              }
        }
    }

`;

const ACTUALIZAR_EMPRESA = gql`
mutation ActualizarEmpresa($id: ID!, $input: EmpresaInput) {
    actualizarEmpresa(id: $id, input: $input) {
      id
      nombre
      cif
      representante
      nif
      telefono
      direccion
      ciclos {
        id
        nombreCorto
        nombreLargo
      }
    }
  }
`;

const OBTENER_CICLOS = gql`
  query Query {
      obtenerCiclos {
          id
          nombreCorto
          nombreLargo
      }
  }
`;

export default function EditarEmpresa() {

    // obtener el ID actual
    const router = useRouter();
    const { pid } = router.query

    // Mensaje de alerta
    const [mensaje, guardarMensaje] = useState(null);

    const [mostrarComponente, setMostrarComponente] = useState(false);

    const { data: dataCiclos, loading: loadingCiclos, error: errorCiclos } = useQuery(OBTENER_CICLOS);

    const [selectedOptions, setSelectedOptions] = useState([]);

    const handleSelect = () => {
        console.log(selectedOptions);
    };

    // Consultar para obtener la empresa
    const { data: dataEmpresa, loading: loadingEmpresa, error: errorEmpresa } = useQuery(OBTENER_EMPRESA, {
        variables: {
            id: pid
        }
    });

    // Actualizar la empresa
    const [actualizarEmpresa] = useMutation(ACTUALIZAR_EMPRESA);


    // Schema de validacion
    const schemaValidacion = Yup.object({
        nombre: Yup.string()
            .required('El nombre de la empresa es obligatorio'),
        cif: Yup.string()
            .matches(/([ABCDEFGHJKLMNPQRSUVW])[0-9]{7}([ABCDEFGHJKLMNPQRSUVW]|[0-9])/, 'El formato del cif es incorrecto')
            .required('El cif es obligatorio'),
        representante: Yup.string()
            .required('El nombre del representante es obligatorio'),
        nif: Yup.string()
            .matches(/^\d{8}[a-zA-Z]$/, 'El formato del dni es incorrecto')
            .required('El nif es obligatorio'),
        telefono: Yup.string()
            .matches(/^[0-9]{9}$/, 'El formato del teléfono es incorrecto')
            .required('El teléfono es obligatorio'),
        direccion: Yup.string()
            .required('La dirección es obligatoria'),
    });


    if (loadingEmpresa || loadingCiclos) return 'Cargando...';

    if (!pid) return 'Cargando...';

    const { obtenerEmpresa } = dataEmpresa;
    const { obtenerCiclos } = dataCiclos;
    const { ciclos } = obtenerEmpresa


    // Modifica la empresa de la BD
    const actualizarInfoEmpresa = async valores => {
        const { nombre, cif, representante, nif, telefono, direccion } = valores;

        console.log(valores);
        let datos = recogerCiclos(selectedOptions);

        try {
            // No se actualizan ciclos
            if (datos == 0) {
                const { data } = await actualizarEmpresa({
                    variables: {
                        id: pid,
                        input: {
                            nombre,
                            cif,
                            representante,
                            nif,
                            telefono,
                            direccion
                        }
                    }
                });
            } else { // Si se actualizan ciclos
                const { data } = await actualizarEmpresa({
                    variables: {
                        id: pid,
                        input: {
                            nombre,
                            cif,
                            representante,
                            nif,
                            telefono,
                            direccion,
                            ciclos: datos
                        }
                    }
                });
            }

            // Mostrar alerta
            Swal.fire(
                'Actualizado',
                'La empresa se actualizó correctamente',
                'success'
            );
            // Redireccionar
            router.push('/empresa/empresas');

        } catch (error) {
            guardarMensaje(error.message);

            setTimeout(() => {
                guardarMensaje(null);
            }, 3000);
        }
    }

    const recogerCiclos = (opciones) => {

        let array = [];
        opciones.map(a => {
            obtenerCiclos.filter(ciclo => ciclo.nombreCorto === a.nombreCorto ? (
                array.push({ id: ciclo.id, nombreCorto: ciclo.nombreCorto, nombreLargo: ciclo.nombreLargo })

            ) : (''));
            //console.log(a)
        })

        return array;
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

                {mensaje && mostrarMensaje()}
                <section className="overflow-hidden text-gray-700 ">
                    <div className="py-5 md:px-12 text-gray-800 rounded-xl">
                        <div className="container mx-auto xl:px-32">
                            <div className="grid lg:grid-cols-1 gap-12 items-center">
                                <div className="mb-12 lg:mb-0 ">
                                    <div className="block rounded-lg shadow-lg bg-white px-6 py-12 md:px-12">
                                        <Formik validationSchema={schemaValidacion} enableReinitialize
                                            initialValues={obtenerEmpresa}
                                            onSubmit={(valores) => {
                                                actualizarInfoEmpresa(valores);
                                            }}
                                        >

                                            {props => {

                                                return (
                                                    <form onSubmit={props.handleSubmit}>
                                                        <div className="pb-2">
                                                            <h1 className="font-medium text-gray-500 text-md" style={{ color: "hsl(217, 79%, 78%)" }}>Datos Empresa</h1>
                                                        </div>
                                                        <div className="grid xl:grid-cols-3 xl:gap-6">
                                                            <div className="relative z-0 w-full mb-6 group">
                                                                <label htmlFor="nombre" className="font-medium  text-sm text-gray-500 dark:text-gray-400 ">Nombre</label>
                                                                <input type="text" name="nombre" id="nombre" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer capitalize"
                                                                    onChange={props.handleChange}
                                                                    onBlur={props.handleBlur}
                                                                    value={props.values.nombre} />
                                                                {props.touched.nombre && props.errors.nombre ? (
                                                                    <div className='my-2 bg-red-100 border-l-4 text-sm border-red-500 text-red-700 p-4'>
                                                                        <p className='font-bold'>Error</p>
                                                                        <p>{props.errors.nombre}</p>
                                                                    </div>
                                                                ) : null}
                                                            </div>
                                                            <div className="relative z-0 w-full mb-6 group">
                                                                <label htmlFor="cif" className="font-medium  text-sm text-gray-500 dark:text-gray-400 ">Cif</label>
                                                                <input type="text" name="cif" id="cif" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer uppercase"
                                                                    onChange={props.handleChange}
                                                                    onBlur={props.handleBlur}
                                                                    value={props.values.cif} />
                                                                {props.touched.cif && props.errors.cif ? (
                                                                    <div className='my-2 bg-red-100 border-l-4 text-sm border-red-500 text-red-700 p-4'>
                                                                        <p className='font-bold'>Error</p>
                                                                        <p>{props.errors.cif}</p>
                                                                    </div>
                                                                ) : null}
                                                            </div>
                                                            <div className="relative z-0 w-full mb-6 group">
                                                                <label htmlFor="direccion" className="font-medium  text-sm text-gray-500 dark:text-gray-400">Dirección</label>
                                                                <input type="text" name="direccion" id="direccion" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer capitalize"
                                                                    onChange={props.handleChange}
                                                                    onBlur={props.handleBlur}
                                                                    value={props.values.direccion}
                                                                />
                                                                {props.touched.direccion && props.errors.direccion ? (
                                                                    <div className='my-2 bg-red-100 border-l-4 text-sm  border-red-500 text-red-700 p-4'>
                                                                        <p className='font-bold'>Error</p>
                                                                        <p>{props.errors.direccion}</p>
                                                                    </div>
                                                                ) : null}
                                                            </div>
                                                        </div>

                                                        <div className="pb-2">
                                                            <h1 className="font-medium text-gray-500 text-md" style={{ color: "hsl(217, 79%, 78%)" }}>Datos Representante</h1>
                                                        </div>

                                                        <div className="grid xl:grid-cols-3 xl:gap-6">

                                                            <div className="relative z-0 w-full mb-6 group">
                                                                <label htmlFor="representante" className="font-medium  text-sm text-gray-500 dark:text-gray-400">Nombre</label>
                                                                <input type="text" name="representante" id="representante" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer capitalize"
                                                                    onChange={props.handleChange}
                                                                    onBlur={props.handleBlur}
                                                                    value={props.values.representante} />
                                                                {props.touched.representante && props.errors.representante ? (
                                                                    <div className='my-2 bg-red-100 border-l-4 text-sm border-red-500 text-red-700 p-4'>
                                                                        <p className='font-bold'>Error</p>
                                                                        <p>{props.errors.representante}</p>
                                                                    </div>
                                                                ) : null}
                                                            </div>
                                                            <div className="relative z-0 w-full mb-6 group">
                                                                <label htmlFor="nif" className="font-medium  text-sm text-gray-500 dark:text-gray-400 ">Nif</label>
                                                                <input type="text" name="nif" id="nif" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer uppercase"
                                                                    onChange={props.handleChange}
                                                                    onBlur={props.handleBlur}
                                                                    value={props.values.nif} />
                                                                {props.touched.nif && props.errors.nif ? (
                                                                    <div className='my-2 bg-red-100 border-l-4 text-sm border-red-500 text-red-700 p-4'>
                                                                        <p className='font-bold'>Error</p>
                                                                        <p>{props.errors.nif}</p>
                                                                    </div>
                                                                ) : null}
                                                            </div>
                                                            <div className="relative z-0 w-full mb-6 group">
                                                                <label htmlFor="telefono" className="font-medium  text-sm text-gray-500 dark:text-gray-400 ">Teléfono</label>
                                                                <input type="number" name="telefono" id="telefono" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
                                                                    onChange={props.handleChange}
                                                                    onBlur={props.handleBlur}
                                                                    value={props.values.telefono} />
                                                                {props.touched.telefono && props.errors.telefono ? (
                                                                    <div className='my-2 bg-red-100 border-l-4 text-sm border-red-500 text-red-700 p-4'>
                                                                        <p className='font-bold'>Error</p>
                                                                        <p>{props.errors.telefono}</p>
                                                                    </div>
                                                                ) : null}
                                                            </div>
                                                        </div>

                                                        <div className="pb-4 flex justify-start">
                                                            <h1 className="font-medium text-gray-500 text-md pr-6" style={{ color: "hsl(217, 79%, 78%)" }}>Ciclos</h1>
                                                            <div className="flex items-center">
                                                                <input id="checked-checkbox" type="checkbox" value="" onClick={() => setMostrarComponente(!mostrarComponente)} className="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600" />
                                                                <label htmlFor="checked-checkbox" className="ml-2 text-sm font-medium text-gray-900 dark:text-gray-300">¿Editar Ciclos?</label>
                                                            </div>
                                                        </div>

                                                        <div className={mostrarComponente ? "show-element" : null}>
                                                            {mostrarComponente ||
                                                                (ciclos != 0 ?
                                                                    (ciclos?.map(ciclo => (
                                                                        <p className="font-medium text-md" key={ciclo.id}>- {ciclo.nombreLargo}</p>
                                                                    ))) : (<p className="text-red-500 pt-2">No exiten ciclos relaccionados con esta empresa</p>))
                                                            }
                                                        </div>
                                                        <div className={mostrarComponente ? "show-element" : null}>
                                                            {mostrarComponente &&
                                                                <div>
                                                                    <div className="relative z-0 w-full mb-6 group">
                                                                        <Select
                                                                            isMulti
                                                                            options={obtenerCiclos}
                                                                            onChange={(item) => setSelectedOptions(item)}
                                                                            className="select"
                                                                            getOptionValue={(obtenerCiclos) => obtenerCiclos.id}
                                                                            getOptionLabel={(obtenerCiclos) => obtenerCiclos.nombreLargo}
                                                                            isClearable={true}
                                                                            isSearchable={true}
                                                                            isDisabled={false}
                                                                            isLoading={false}
                                                                            isRtl={false}
                                                                            closeMenuOnSelect={false}
                                                                            placeholder="Selecciona algun ciclo"
                                                                            noOptionsMessage={() => "No hay resultados"}
                                                                        />
                                                                    </div>
                                                                    <div className="float-left p-2 bg-blue-200 border-l-4 border-cyan-700">
                                                                        <p className="font-bold text-cyan-800">Información</p>
                                                                        <p className="text-sm font-medium">Si se actualizan los ciclos, recuerda que debes de volver a señalar los que tenias anteriormente o se borrarán.</p>
                                                                    </div>
                                                                </div>
                                                            }

                                                        </div>


                                                        <input type="submit" data-mdb-ripple="true" data-mdb-ripple-color="light" className="inline-block px-6 py-2.5 mb-2 mt-4 w-full bg-blue-600 text-white font-medium text-xs leading-tight uppercase rounded shadow-md hover:bg-blue-700 hover:shadow-lg focus:bg-blue-700 focus:shadow-lg focus:outline-none focus:ring-0 active:bg-blue-800 active:shadow-lg transition duration-150 ease-in-out"
                                                            value="Actualizar datos" />
                                                    </form>
                                                )
                                            }}
                                        </Formik>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </section>

            </Layout>

        </>
    );
}