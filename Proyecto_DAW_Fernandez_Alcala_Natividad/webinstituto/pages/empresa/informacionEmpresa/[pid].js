import React from "react";
import { useRouter } from "next/router";
import Layout from "../../../components/Layout";
import { useQuery, useMutation, gql } from "@apollo/client";
import Swal from 'sweetalert2';
import Image from 'next/image';
import Empleado from "../../../components/Empleado";
import Link from 'next/link';

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

const OBTENER_EMPLEADOS_EMPRESA = gql`
    query Query($id: ID!) {
        obtenerEmpleadosEmpresa(id: $id) {
            id
            nombre
            apellido
            nif
            telefono
            email 
            empresa           
        }
    }
`;

export default function InformacionEmpresa() {

    // obtener el ID actual
    const router = useRouter();
    const { pid } = router.query

    // Consultar para obtener la empresa
    const { data: dataEmpresa, loading: loadingEmpresa, error: errorEmpresa } = useQuery(OBTENER_EMPRESA, {
        variables: {
            id: pid
        }
    });

    // Consultar para obtener los empleados de la empresa
    const { data: dataEmpleados, loading: loadingEmpleados, error: errorEmpleados } = useQuery(OBTENER_EMPLEADOS_EMPRESA, {
        variables: {
            id: pid
        }
    });

    if (loadingEmpresa || loadingEmpleados || !pid) return 'Cargando...';

    const { obtenerEmpresa } = dataEmpresa;

    const { nombre, cif, direccion, representante, nif, telefono } = obtenerEmpresa;

    const { obtenerEmpleadosEmpresa } = dataEmpleados;

    return (
        <>
            <Layout>

                <section className="overflow-hidden text-gray-700 ">
                    <div className="py-5 md:px-12 text-gray-800 rounded-xl">
                        <div className="container mx-auto xl:px-32">
                            <div className="grid lg:grid-cols-1 gap-12 items-center">
                                <div className="mb-12 lg:mb-0 ">
                                    <div className='flex items-end pb-5 pl-2'>
                                        <Image
                                            src="/empresa.png"
                                            alt="Landscape picture"
                                            width={70}
                                            height={70}
                                            priority
                                        />
                                        <h1 className="text-lg md:text-2xl lg:text-2xl xl:text-3xl font-bold tracking-tight pl-2" style={{ color: "hsl(217, 79%, 78%)" }}>{obtenerEmpresa.nombre}</h1>
                                    </div>
                                    <div className="block rounded-lg shadow-lg bg-white px-6 py-12 md:px-12">

                                        <div className="grid xl:grid-cols-2 xl:gap-6">
                                            <div className="relative z-0 w-full mb-6 group">
                                                <label htmlFor="cif" className="font-medium  text-sm text-gray-500 dark:text-gray-400 ">Cif</label>
                                                <input type="cif" name="cif" id="cif" disabled className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"

                                                    value={cif} />
                                            </div>
                                            <div className="relative z-0 w-full mb-6 group">
                                                <label htmlFor="direccion" className="font-medium  text-sm text-gray-500 dark:text-gray-400">Dirección</label>
                                                <input type="direccion" name="direccion" id="direccion" disabled className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
                                                    value={direccion}
                                                />
                                            </div>
                                        </div>

                                        <div className="pb-2">
                                            <h1 className="font-medium text-gray-500 text-md" style={{ color: "hsl(217, 79%, 78%)" }}>Información Representante</h1>
                                        </div>

                                        <div className="grid xl:grid-cols-3 xl:gap-6">

                                            <div className="relative z-0 w-full mb-6 group">
                                                <label htmlFor="representante" className="font-medium  text-sm text-gray-500 dark:text-gray-400">Nombre</label>
                                                <input type="representante" name="representante" id="representante" disabled className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"

                                                    value={representante} />
                                            </div>
                                            <div className="relative z-0 w-full mb-6 group">
                                                <label htmlFor="nif" className="font-medium  text-sm text-gray-500 dark:text-gray-400 ">Nif</label>
                                                <input type="nif" name="nif" id="nif" disabled className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"

                                                    value={nif} />
                                            </div>
                                            <div className="relative z-0 w-full mb-6 group">
                                                <label htmlFor="telefono" className="font-medium  text-sm text-gray-500 dark:text-gray-400 ">Teléfono</label>
                                                <input type="telefono" name="telefono" id="telefono" disabled className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"

                                                    value={telefono} />
                                            </div>
                                        </div>

                                        <div className="flex justify-start pb-2">
                                            <h1 className="font-medium text-gray-500 text-md" style={{ color: "hsl(217, 79%, 78%)" }}>Empleados</h1>
                                            <Link href={{ pathname: '../../empleado/nuevoEmpleado/[id]', query: { id: pid } }}
                                            >
                                                <button type="button" className="inline-block px-4 mr-1 ">
                                                    <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6 stroke-blue-500 active:stroke-blue-900 focus:shadow-lg" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                                                        <path strokeLinecap="round" strokeLinejoin="round" d="M18 9v3m0 0v3m0-3h3m-3 0h-3m-2-5a4 4 0 11-8 0 4 4 0 018 0zM3 20a6 6 0 0112 0v1H3v-1z" />
                                                    </svg>
                                                </button>
                                            </Link>
                                        </div>

                                        <div className="relative z-0 w-full mb-6 group">
                                            {obtenerEmpleadosEmpresa != 0 ?
                                                (obtenerEmpleadosEmpresa?.map(empleado => (
                                                    <Empleado key={empleado.id} empleado={empleado} />
                                                ))) : (<p className="text-red-500 pt-2">No exiten empleados relaccionados con esta empresa</p>)}
                                        </div>

                                        <div className="relative z-0 w-full mb-6 group divide-y-2 divide-gray-300">
                                            <div></div>
                                            <div></div>
                                        </div>
                                        <div className="pb-2">
                                            <h1 className="font-medium text-gray-500 text-md" style={{ color: "hsl(217, 79%, 78%)" }}>Ciclos</h1>
                                        </div>

                                        <div className="relative z-0 w-full mb-6 group">
                                            {obtenerEmpresa.ciclos != 0 ?
                                                (obtenerEmpresa.ciclos?.map(ciclo => (
                                                    <p className="font-medium" key={ciclo.id}>- {ciclo.nombreLargo}</p>
                                                ))) : (<p className="text-red-500 pt-2">No exiten ciclos relaccionados con esta empresa</p>)}
                                        </div>

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


