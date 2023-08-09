import React, { useState } from 'react';
import Layout from "../../components/Layout";
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { useQuery, useMutation, gql } from '@apollo/client';
import { useRouter } from "next/router";
import Image from 'next/image';
import Select from 'react-select';

const NUEVA_EMPRESA = gql`

    mutation NuevaEmpresa($input: EmpresaInput) {
        nuevaEmpresa(input: $input) {
            id
            nombre
            cif
            representante
            nif
            telefono
            direccion
        }
      }
    
    `;

const OBTENER_EMPRESAS = gql`
    query Query {
        obtenerEmpresas {
            id
            nombre
            cif
            representante
            nif
            telefono
            direccion
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

export default function NuevaEmpresa() {

    const router = useRouter();

    // Mensaje de alerta
    const [mensaje, guardarMensaje] = useState(null);

    const { data, loading, error } = useQuery(OBTENER_CICLOS);

    const [selectedOptions, setSelectedOptions] = useState([]);

    const handleSelect = () => {
        console.log(selectedOptions);
    };

    const [nuevaEmpresa] = useMutation(NUEVA_EMPRESA, {
        update(cache, { data: { nuevaEmpresa } }) {
            // obtener el objeto de cache que deseamos actualizar
            const { obtenerEmpresas } = cache.readQuery({ query: OBTENER_EMPRESAS });

            // Reescribimos el cache (el caceh nunca se debe modificar)
            cache.writeQuery({
                query: OBTENER_EMPRESAS,
                data: {
                    obtenerEmpresas: [...obtenerEmpresas, nuevaEmpresa]
                }
            });
        }
    })

    const formik = useFormik({
        initialValues: {
            nombre: '',
            cif: '',
            representante: '',
            nif: '',
            telefono: '',
            direccion: ''
        },
        validationSchema: Yup.object({
            nombre: Yup.string()
                .required('El nombre de la empresa es obligatorio'),
            cif: Yup.string()
                .matches(/^([ABCDEFGHJKLMNPQRSUVW])(\d{7})([0-9A-J])$/, 'El formato del cif es incorrecto')
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
        }),
        onSubmit: async valores => {
            console.log(valores);

            let datos = recogerCiclos(selectedOptions);

            if (datos == 0) {
                guardarMensaje("Debes de seleccionar al menos un ciclo");
                setTimeout(() => {
                    guardarMensaje(null);
                }, 3000);
            } else {

                const { nombre, cif, representante, nif, telefono, direccion } = valores;
                try {
                    const { data } = await nuevaEmpresa({
                        variables: {
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

                    router.push('/empresa/empresas'); // Redirrecionar hacia empresas
                } catch (error) {
                    guardarMensaje(error.message);

                    setTimeout(() => {
                        guardarMensaje(null);
                    }, 3000);
                    //console.log(error);
                }
            }
        }
    })

    if (loading) return null;

    const { obtenerCiclos } = data;

    const recogerCiclos = (opciones) => {

        let array = [];
        opciones.map(a => {
            obtenerCiclos.filter(ciclo => ciclo.nombreCorto === a.nombreCorto ? (
                array.push({ id: ciclo.id, nombreCorto: ciclo.nombreCorto, nombreLargo: ciclo.nombreLargo })

            ) : (''));
            console.log(a)
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
        <Layout>
            {mensaje && mostrarMensaje()}       

            <section className="overflow-hidden text-gray-700 ">
                <div className="py-5 md:px-12 text-gray-800 rounded-xl">
                    <div className="container mx-auto xl:px-32">
                        <div className="grid lg:grid-cols-1 gap-12 items-center">
                            <div className="mb-12 lg:mb-0 ">
                                <div className="block rounded-lg shadow-lg bg-white px-6 py-12 md:px-12">
                                    <form onSubmit={formik.handleSubmit}>
                                        <div className="pb-2">
                                            <h1 className="font-medium text-gray-500 text-md" style={{ color: "hsl(217, 79%, 78%)" }}>Datos Empresa</h1>
                                        </div>
                                        <div className="grid xl:grid-cols-3 xl:gap-6">
                                            <div className="relative z-0 w-full mb-6 group">
                                                <label htmlFor="nombre" className="font-medium  text-sm text-gray-500 dark:text-gray-400 ">Nombre</label>
                                                <input type="text" name="nombre" id="nombre" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
                                                    onChange={formik.handleChange}
                                                    onBlur={formik.handleBlur}
                                                    value={formik.values.nombre} />
                                                {formik.touched.nombre && formik.errors.nombre ? (
                                                    <div className='my-2 bg-red-100 border-l-4 text-sm border-red-500 text-red-700 p-4'>
                                                        <p className='font-bold'>Error</p>
                                                        <p>{formik.errors.nombre}</p>
                                                    </div>
                                                ) : null}
                                            </div>
                                            <div className="relative z-0 w-full mb-6 group">
                                                <label htmlFor="cif" className="font-medium  text-sm text-gray-500 dark:text-gray-400 ">Cif</label>
                                                <input type="text" name="cif" id="cif" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
                                                    onChange={formik.handleChange}
                                                    onBlur={formik.handleBlur}
                                                    value={formik.values.cif} />
                                                {formik.touched.cif && formik.errors.cif ? (
                                                    <div className='my-2 bg-red-100 border-l-4 text-sm border-red-500 text-red-700 p-4'>
                                                        <p className='font-bold'>Error</p>
                                                        <p>{formik.errors.cif}</p>
                                                    </div>
                                                ) : null}
                                            </div>
                                            <div className="relative z-0 w-full mb-6 group">
                                                <label htmlFor="direccion" className="font-medium  text-sm text-gray-500 dark:text-gray-400">Dirección</label>
                                                <input type="text" name="direccion" id="direccion" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
                                                    onChange={formik.handleChange}
                                                    onBlur={formik.handleBlur}
                                                    value={formik.values.direccion}
                                                />
                                                {formik.touched.direccion && formik.errors.direccion ? (
                                                    <div className='my-2 bg-red-100 border-l-4 text-sm  border-red-500 text-red-700 p-4'>
                                                        <p className='font-bold'>Error</p>
                                                        <p>{formik.errors.direccion}</p>
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
                                                <input type="text" name="representante" id="representante" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
                                                    onChange={formik.handleChange}
                                                    onBlur={formik.handleBlur}
                                                    value={formik.values.representante} />
                                                {formik.touched.representante && formik.errors.representante ? (
                                                    <div className='my-2 bg-red-100 border-l-4 text-sm border-red-500 text-red-700 p-4'>
                                                        <p className='font-bold'>Error</p>
                                                        <p>{formik.errors.representante}</p>
                                                    </div>
                                                ) : null}
                                            </div>
                                            <div className="relative z-0 w-full mb-6 group">
                                                <label htmlFor="nif" className="font-medium  text-sm text-gray-500 dark:text-gray-400 ">Nif</label>
                                                <input type="text" name="nif" id="nif" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
                                                    onChange={formik.handleChange}
                                                    onBlur={formik.handleBlur}
                                                    value={formik.values.nif} />
                                                {formik.touched.nif && formik.errors.nif ? (
                                                    <div className='my-2 bg-red-100 border-l-4 text-sm border-red-500 text-red-700 p-4'>
                                                        <p className='font-bold'>Error</p>
                                                        <p>{formik.errors.nif}</p>
                                                    </div>
                                                ) : null}
                                            </div>
                                            <div className="relative z-0 w-full mb-6 group">
                                                <label htmlFor="telefono" className="font-medium  text-sm text-gray-500 dark:text-gray-400 ">Teléfono</label>
                                                <input type="number" name="telefono" id="telefono" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
                                                    onChange={formik.handleChange}
                                                    onBlur={formik.handleBlur}
                                                    value={formik.values.telefono} />
                                                {formik.touched.telefono && formik.errors.telefono ? (
                                                    <div className='my-2 bg-red-100 border-l-4 text-sm border-red-500 text-red-700 p-4'>
                                                        <p className='font-bold'>Error</p>
                                                        <p>{formik.errors.telefono}</p>
                                                    </div>
                                                ) : null}
                                            </div>
                                        </div>

                                        <div className="pb-2">
                                            <h1 className="font-medium text-gray-500 text-md" style={{ color: "hsl(217, 79%, 78%)" }}>Ciclos</h1>
                                        </div>

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
                                        <input type="submit" data-mdb-ripple="true" data-mdb-ripple-color="light" className="inline-block px-6 py-2.5 mb-2 mt-4 w-full bg-blue-600 text-white font-medium text-xs leading-tight uppercase rounded shadow-md hover:bg-blue-700 hover:shadow-lg focus:bg-blue-700 focus:shadow-lg focus:outline-none focus:ring-0 active:bg-blue-800 active:shadow-lg transition duration-150 ease-in-out"
                                            value="Registrar Empresa" />
                                    </form>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </section>
        </Layout>

    );
}