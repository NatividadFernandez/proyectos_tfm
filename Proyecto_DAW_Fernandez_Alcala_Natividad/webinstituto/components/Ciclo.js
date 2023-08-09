import React from 'react';
import Swal from 'sweetalert2';
import { useMutation, gql } from "@apollo/client";
import Router from "next/router";
import Image from 'next/image';

const ELIMINAR_CICLO = gql`
    mutation EliminarCiclo($id: ID!) {
        eliminarCiclo(id: $id)
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

export default function Ciclo({ ciclo }) {

    // Mutation para eliminar ciclo
    const [eliminarCiclo] = useMutation(ELIMINAR_CICLO, {
        update(cache) {
            // obtener una copia del objeto de cache
            const { obtenerCiclos } = cache.readQuery({ query: OBTENER_CICLOS });

            // Reescribir el cache
            cache.writeQuery({
                query: OBTENER_CICLOS,
                data: {
                    obtenerCiclos: obtenerCiclos.filter(cicloActual => cicloActual.id !== id)
                }
            });
        }
    });

    const { nombreCorto, nombreLargo, id } = ciclo;

    // Eliminar una Empresa
    const confirmarEliminarCiclo = () => {
        Swal.fire({
            title: '¿Deseas eliminar este ciclo?',
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
                    const { data } = await eliminarCiclo({
                        variables: {
                            id
                        }
                    })
                    //console.log(data);

                    Swal.fire(
                        'Eliminado',
                        data.eliminarCiclo, // Al devolvernos un mensaje, los mostramos en el alert
                        'success'
                    )
                } catch (error) {
                    console.log(error);
                }
            }
        })

    }

    // Editar un Ciclo
    const editarCiclo = () => {
        Router.push({
            pathname: "/ciclos/editarCiclo/[id]",
            query: { id }
        })
    }


    return (
        <div className="flex flex-wrap md:w-1/2 lg:w-1/3 xl:w-1/3 ">
            <div className="w-full p-1 md:p-2">
                <div className="block p-6 rounded-lg shadow-lg bg-white max-w-sm">
                    <div className='text-center'>
                        <a href="#!">
                            <Image
                                src="/educa.png"
                                alt="Landscape picture"
                                width={130}
                                height={130}
                                priority
                            />
                        </a>
                    </div>
                    <div className=''>
                        <h5 className="text-gray-900 text-xl leading-tight font-medium mb-2"><span className='bg-orange-100 h-2.5 w-2.5 px-2 rounded uppercase'>{nombreCorto}</span></h5>
                        <div className='md:h-28 lg:h-28 xl:h-24'>
                            <p className="text-gray-700 mb-4 sm:text-md md:text-lg lg:text-lg xl:text-lg capitalize">
                                {nombreLargo}
                            </p>
                        </div>
                        <div className='flex justify-around'>
                            <button type="button" className=" inline-block px-6 py-2.5 bg-green-400 text-white font-medium text-xs leading-tight uppercase rounded shadow-md hover:bg-green-700 hover:shadow-lg focus:bg-green-700 focus:shadow-lg focus:outline-none focus:ring-0 active:bg-green-800 active:shadow-lg transition duration-150 ease-in-out" onClick={() => editarCiclo()}>
                                <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                                    <path strokeLinecap="round" strokeLinejoin="round" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" />
                                </svg>
                            </button>
                            <button type="button" className=" inline-block px-6 py-2.5 bg-red-600 text-white font-medium text-xs leading-tight uppercase rounded shadow-md hover:bg-red-700 hover:shadow-lg focus:bg-red-700 focus:shadow-lg focus:outline-none focus:ring-0 active:bg-red-800 active:shadow-lg transition duration-150 ease-in-out" onClick={() => confirmarEliminarCiclo()}>
                                <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                                    <path strokeLinecap="round" strokeLinejoin="round" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
                                </svg>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>


    );
}