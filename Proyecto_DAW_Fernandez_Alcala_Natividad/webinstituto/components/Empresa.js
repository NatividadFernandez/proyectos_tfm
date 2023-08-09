import React from 'react';
import Swal from 'sweetalert2';
import { useMutation, gql } from "@apollo/client";
import Router from "next/router";

const ELIMINAR_EMPRESA = gql`
    mutation EliminarEmpresa($id: ID!) {
        eliminarEmpresa(id: $id)
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
            ciclos {
                id
                nombreCorto
                nombreLargo
            }
        }
    }
`;

export default function Empresa({ empresa }) {

    // Mutation para eliminar empresa
    const [eliminarEmpresa] = useMutation(ELIMINAR_EMPRESA, {
        update(cache) {
            // obtener una copia del objeto de cache
            const { obtenerEmpresas } = cache.readQuery({ query: OBTENER_EMPRESAS });

            // Reescribir el cache
            cache.writeQuery({
                query: OBTENER_EMPRESAS,
                data: {
                    obtenerEmpresas: obtenerEmpresas.filter(empresaActual => empresaActual.id !== id)
                }
            });
        }
    });

    const { nombre, cif, representante, telefono, direccion, ciclos, id } = empresa;

    // Eliminar una Empresa
    const confirmarEliminarEmpresa = () => {
        Swal.fire({
            title: '¿Deseas eliminar esta empresa?',
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
                    const { data } = await eliminarEmpresa({
                        variables: {
                            id
                        }
                    })
                    //console.log(data);

                    Swal.fire(
                        'Eliminado',
                        data.eliminarEmpresa, // Al devolvernos un mensjae, los mostrams en el alert
                        'success'
                    )
                } catch (error) {
                    console.log(error);
                }
            }
        })

    }

    // Editar una Empresa
    const editarEmpresa = () => {
        Router.push({
            pathname: "/empresa/editarEmpresa/[id]",
            query: { id }
        })
    }

    // Informacion Empresa
    const infoEmpresa = () => {
        Router.push({
            pathname: "/empresa/informacionEmpresa/[id]",
            query: { id }
        })
    }


    return (
        <tr className='hover:bg-gray-300'>
            <td className="border px-4 py-2 capitalize">{nombre}</td>
            <td className="border px-4 py-2">{cif}</td>
            <td className="border px-4 py-2 capitalize">{representante}</td>
            <td className="border px-4 py-2">{telefono}</td>
            <td className="border px-4 py-2 capitalize">{direccion}</td>
            <td className="border px-4 py-2">
                <div className="flex space-x-2 justify-center">

                    <button
                        type='button'
                        className='bg-blue-500 w-full sm:w-auto font-bold uppercase text-xs rounded py-1 px-2 text-white shadow-md '
                        onClick={() => infoEmpresa()}
                    >
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                            <path strokeLinecap="round" strokeLinejoin="round" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                        </svg>
                    </button>

                    <button
                        type='button'
                        className='bg-green-500 w-full sm:w-auto font-bold uppercase text-xs rounded py-1 px-2 text-white shadow-md '
                        onClick={() => editarEmpresa()}
                    >
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                            <path strokeLinecap="round" strokeLinejoin="round" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" />
                        </svg>
                    </button>
                    <button
                        type='button'
                        className='bg-red-500 w-full sm:w-auto font-bold uppercase text-xs rounded py-1 px-2 text-white shadow-md'
                        onClick={() => confirmarEliminarEmpresa()}
                    >
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                            <path strokeLinecap="round" strokeLinejoin="round" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
                        </svg>
                    </button>


                </div>
            </td>
        </tr>
    );
}