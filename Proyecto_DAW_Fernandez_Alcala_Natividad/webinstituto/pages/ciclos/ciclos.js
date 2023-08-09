import Layout from "../../components/Layout";
import { useQuery, gql } from "@apollo/client";
import { useRouter } from "next/router";
import Link from 'next/link';
import Ciclo from "../../components/Ciclo";
import React, { useState } from "react";
import Image from 'next/image';

const OBTENER_CICLOS = gql`
  query Query {
      obtenerCiclos {
          id
          nombreCorto
          nombreLargo
      }
  }
`;

export default function Ciclos() {

  const [query, setQuery] = useState('');

  // Consulta de Apollo
  const { data, loading, error } = useQuery(OBTENER_CICLOS);

  if (loading) return 'Cargando...';

  if (!data.obtenerCiclos) {
    return router.push('/login');
  }

  const keys = ["nombreCorto", "nombreLargo"];

  const search = (data) => {
    return data.filter((ciclo) => keys.some((key) => ciclo[key].toLowerCase().includes(query))
    )
  }

  return (
    <div>
      <Layout>      

        <div className="flex mt-5">
          <Link href='/ciclos/nuevoCiclo'>
            <a className="flex-shrink-0 z-10 inline-flex items-center py-2.5 px-4 text-sm font-medium text-center text-gray-900 bg-gray-100 border border-gray-300 rounded-l-lg hover:bg-gray-200 focus:ring-4 focus:outline-none focus:ring-gray-100 dark:bg-gray-700 dark:hover:bg-gray-600 dark:focus:ring-gray-700 dark:text-white dark:border-gray-600" type="button">Nuevo Ciclo
              <svg xmlns="http://www.w3.org/2000/svg" className="ml-1 w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                <path strokeLinecap="round" strokeLinejoin="round" d="M12 9v3m0 0v3m0-3h3m-3 0H9m12 0a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
            </a>
          </Link>
          <div className="relative w-full">
            <input type="search" className="block p-2.5 w-full z-20 text-sm text-gray-900 bg-gray-50 rounded-r-lg border-l-gray-50 border-l-2 border border-gray-300  focus:ring-blue-500 focus:border-blue-500  focus:outline-none dark:bg-gray-700 dark:border-l-gray-700  dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:border-blue-500" placeholder="Buscador..." onChange={e => setQuery(e.target.value)} />
            <button type="text" className="absolute top-0 right-0 p-2.5 text-sm font-medium text-white bg-blue-700 rounded-r-lg border border-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"><svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path></svg></button>
          </div>
        </div>
        <section className="overflow-hidden text-gray-700 ">         
          <div className="container py-2 px-5 mx-auto pt-6 md:pt-12 md:px-16 lg:pt-12 lg:px-32">
            <div className=" md:flex md:flex-wrap xl:flex xl:flex-wrap lg:flex lg:flex-wrap -m-1 md:-m-2">              

              {search(data.obtenerCiclos) == 0 ?
                (
                  <div className="flex flex-wrap sm:w-1/3 md:w-2/3 m-auto xl:m-auto">
                    <div className="w-full p-1 md:p-2">
                      <div className="block p-6 rounded-lg shadow-lg bg-white">
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-20 w-50 pb-3 m-auto" fill="none" viewBox="0 0 24 24" stroke="#eab308" strokeWidth="2">
                          <path strokeLinecap="round" strokeLinejoin="round" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
                        </svg>
                        <h5 className="text-gray-900 md:text-sm lg:text-xl xl:text-xl leading-tight font-medium mb-2 text-center">Información</h5>
                        <p className="text-gray-700 md:text-sm lg:text-base xl:text-base mb-4 text-center">
                          No se ha encontrado ningún ciclo
                        </p>
                      </div>
                    </div>
                  </div>
                ) :

                (search(data.obtenerCiclos).map(ciclo => (
                  <Ciclo key={ciclo.id} ciclo={ciclo} />
                )))

              }              
            </div>
          </div>
        </section>
      </Layout>
    </div>
  )
}