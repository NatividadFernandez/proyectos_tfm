import React from 'react';
import { useQuery, gql, ApolloConsumer } from '@apollo/client';
import { useRouter } from "next/router";
//import { getSession } from "next-auth/react";

const OBTENER_USUARIO = gql`
    query ObtenerUsuario {
        obtenerUsuario {
            id
            nombre
            apellido
        }
    }
`;

export default function Header() {

    const router = useRouter();

    //query de apollo
    const { data, loading, error } = useQuery(OBTENER_USUARIO);

    //console.log(data);
    //console.log(loading);
    //console.log(error);

    // Proteger que no accedamos a data antes de tener resultados
    if (loading) return 'Cargando...';

    // Si no hay información
    if (!data.obtenerUsuario) {
        router.push('/login');
    }

    //const { nombre, apellido } = data.obtenerUsuario;

    const cerrarSesion = (usuario) => {
        localStorage.removeItem('token');
        usuario.resetStore();
        router.push('/login');
    }

    const mensajePagina = (ruta) => {

        if (data.obtenerUsuario) {

            switch (ruta) {
                case "/":
                    return '¡Hola ' + data.obtenerUsuario.nombre + ' ' + data.obtenerUsuario.apellido + '!';
                    break;
                case "/empresa/empresas":
                    return 'Empresas';
                    break;
                case "/empresa/nuevaEmpresa":
                    return 'Nueva Empresa';
                    break;
                case "/empresa/editarEmpresa/[pid]":
                    return 'Editar Empresa';
                    break;
                case "/empresa/informacionEmpresa/[pid]":
                    return 'Información Empresa';
                    break;
                case "/empleado/nuevoEmpleado/[pid]":
                    return 'Nuevo Empleado';
                    break;
                case "/ciclos/ciclos":
                    return 'Ciclos';
                    break;
                case "/ciclos/nuevoCiclo":
                    return 'Nuevo Ciclo';
                    break;
                case "/ciclos/editarCiclo/[pid]":
                    return 'Editar Ciclo';
                    break;
                case "/configUsuario/configuracion":
                    return 'Información Usuario';
                    break;

                default:
                    break;
            }
        }

    }

    //console.log(router.pathname);

    return (
        <>
            <div className='flex justify-between mb-6'>
                <p className="text-3xl md:text-white lg:text-white xl:text-white font-light mr-2">{mensajePagina(router.pathname)}</p>

                <ApolloConsumer>
                    {usuario => (
                        <div className="flex items-center lg:ml-auto">
                            <button type="button" className="inline-block sm:px-4 px-6  py-2.5 bg-white text-blue-600 font-medium text-xs leading-tight uppercase rounded shadow-md hover:bg-gray-100 hover:shadow-lg focus:shadow-lg focus:outline-none focus:ring-0 active:bg-gray-200 active:shadow-lg transition duration-150 ease-in-out"
                                data-mdb-ripple="true" data-mdb-ripple-color="light"
                                onClick={() => cerrarSesion(usuario)}>Cerrar Sesión</button>
                        </div>
                    )}

                </ApolloConsumer>

            </div>
        </>
    );
}

