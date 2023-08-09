import Layout from "../../components/Layout";
import { useQuery, gql } from "@apollo/client";
import { useRouter } from "next/router";
import Link from 'next/link';
import Empresa from "../../components/Empresa";
import React, { useState } from "react";

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

const OBTENER_EMPRESAS_CICLO = gql`
  query Query($id: ID!) {
      obtenerEmpresaCiclo(id: $id) {
        nombre
        cif
        representante
        nif
        telefono
        direccion
      }
  }
`;



export default function Empresas() {

  const router = useRouter();

  const [query, setQuery] = useState('');

  const [showOptions, setShowOptions] = useState(null);

  const [refresh, setRefresh] = useState(true);

  const handleClick = () => {
    setShowOptions(!showOptions);
  }

  const [showCiclos, setShowCiclos] = useState(null);

  const { data: dataCiclos, loading: loadingCiclos, error: errorCiclos } = useQuery(OBTENER_CICLOS);

  const { data: dataEmpresasCiclo, loading: loadingEmpresasCiclo, error: errorEmpresasCiclo } = useQuery(OBTENER_EMPRESAS_CICLO,
    {
      variables: {
        id: showCiclos
      }

    });

  // Consulta de Apollo
  const { data, loading, error } = useQuery(OBTENER_EMPRESAS);

  if (loading || loadingCiclos) return 'Cargando...';

  if (loadingCiclos) return null;

  const { obtenerCiclos } = dataCiclos;


  if (!data.obtenerEmpresas) {
    return router.push('../login');
  }


  const keys = ["nombre", "cif", "representante", "direccion"];


  const search = (data) => {   
    return data.filter((empresa) => keys.some((key) => empresa[key].toLowerCase().includes(query)))

    /*return data.filter(empresa => empresa.nombre.toLowerCase().includes(query) || empresa.cif.toLowerCase().includes(query))*/
  }

  const searchCiclos = (id) => {

    //console.log(id)
  }

  return (
    <div>
      <Layout>
        <div className="flex justify-between">
          <Link href='/empresa/nuevaEmpresa'>
            <a className="inline-block px-6 py-2.5 bg-blue-800 text-white font-medium text-xs leading-tight uppercase rounded shadow-md hover:bg-gray-100 hover:text-blue-800 hover:shadow-lg focus:shadow-lg focus:outline-none focus:ring-0 active:bg-gray-200 active:shadow-lg transition duration-150 ease-in-out" data-mdb-ripple="true" data-mdb-ripple-color="light">Nueva Empresa</a>
          </Link>
          <button onClick={() => setRefresh(true)}>
            <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6 mr-8 stroke-cyan-900" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
              <path strokeLinecap="round" strokeLinejoin="round" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15" />
            </svg>
          </button>
        </div>

        <div className="flex mt-5">
          <div className="relative inline-block text-left">
            <div>
              <button type="button" onClick={() => { handleClick(); setRefresh(false); }} className="flex-shrink-0 z-10 inline-flex items-center py-2.5 px-4 text-sm font-medium text-center text-gray-900 bg-gray-100 border border-gray-300 rounded-l-lg hover:bg-gray-200 focus:ring-4 focus:outline-none focus:ring-gray-100" id="menu-button" aria-expanded="true" aria-haspopup="true">
                Ciclos
                <svg className="-mr-1 ml-2 h-5 w-5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                  <path fillRule="evenodd" d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clipRule="evenodd" />
                </svg>
              </button>
            </div>

            {showOptions &&
              <div className="origin-top-right absolute z-20 left-0 mt-2 w-56 rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5 focus:outline-none" role="menu" aria-orientation="vertical" aria-labelledby="menu-button" tabIndex="-1">
                <div className="py-1" role="none">
                  {obtenerCiclos?.map(ciclo =>
                    <a href="#" key={ciclo.id} className="text-gray-700 block px-4 py-2 text-sm" role="menuitem" tabIndex="-1" onClick={() => { { setShowCiclos(ciclo.id); searchCiclos(ciclo.id) } }} >{ciclo.nombreCorto}</a>
                  )}
                </div>
              </div>
            }
          </div>

          <div className="relative w-full">
            <input type="search" id="search-dropdown" className="block p-2.5 w-full z-20 text-sm text-gray-900 bg-gray-50 rounded-r-lg border-l-gray-50 border-l-2 border border-gray-300  focus:ring-blue-500 focus:border-blue-500  focus:outline-none dark:bg-gray-700 dark:border-l-gray-700  dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:border-blue-500" placeholder="Buscador..." onChange={e => setQuery(e.target.value)} />
            <button type="submit" className="absolute top-0 right-0 p-2.5 text-sm font-medium text-white bg-blue-700 rounded-r-lg border border-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"><svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path></svg></button>
          </div>
        </div>

        <div className="relative overflow-x-auto shadow-md mt-7 ">
          <table className="table-auto shadow-md w-full w-lg">
            <thead className="bg-blue-300">
              <tr className="text-white">
                <th className="px-4 py-2 font-light text-lg">Nombre</th>
                <th className="px-4 py-2 font-light text-lg">Cif</th>
                <th className="px-4 py-2 font-light text-lg">Representante</th>
                <th className="px-4 py-2 font-light text-lg">Teléfono</th>
                <th className="px-4 py-2 font-light text-lg">Dirección</th>
                <th className="px-4 py-2 font-light text-lg">Acciones</th>
              </tr>
            </thead>
            <tbody className="bg-white">

              {refresh &&
                (search(data.obtenerEmpresas) == 0 ?
                  (<tr>
                    <td colSpan={6} className="text-center pt-2 pb-2">
                      <svg xmlns="http://www.w3.org/2000/svg" className="h-20 w-50 pb-3 m-auto" fill="none" viewBox="0 0 24 24" stroke="#eab308" strokeWidth="2">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
                      </svg>
                      <h5 className="text-gray-900 md:text-sm lg:text-xl xl:text-xl leading-tight font-medium mb-2 text-center">Información</h5>
                      <p className="text-gray-700 md:text-sm lg:text-base xl:text-base mb-4 text-center">
                        No se ha encontrado ninguna empresa
                      </p>
                    </td>
                  </tr>) : ((search(data.obtenerEmpresas)?.map(empresa => (
                    <Empresa key={empresa.id} empresa={empresa} />
                  )))))
              }

              {refresh ||
                (search(data.obtenerEmpresas) == 0 ?
                  (<tr>
                    <td colSpan={6} className="text-center pt-2 pb-2">
                      <svg xmlns="http://www.w3.org/2000/svg" className="h-20 w-50 pb-3 m-auto" fill="none" viewBox="0 0 24 24" stroke="#eab308" strokeWidth="2">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
                      </svg>
                      <h5 className="text-gray-900 md:text-sm lg:text-xl xl:text-xl leading-tight font-medium mb-2 text-center">Información</h5>
                      <p className="text-gray-700 md:text-sm lg:text-base xl:text-base mb-4 text-center">
                        No se ha encontrado ninguna empresa
                      </p>
                    </td>
                  </tr>) : (
                    (dataEmpresasCiclo != null) ?
                      ((search(dataEmpresasCiclo.obtenerEmpresaCiclo)?.map(empresa => (
                        <Empresa key={empresa.id} empresa={empresa} />
                      )))) :
                      ((search(data.obtenerEmpresas)?.map(empresa => (
                        <Empresa key={empresa.id} empresa={empresa} />
                      ))))
                  ))
              }
            </tbody>
          </table>
        </div>

      </Layout>
    </div>



  )
}