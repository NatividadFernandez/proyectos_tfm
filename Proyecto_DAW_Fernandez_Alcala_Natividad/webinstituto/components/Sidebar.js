import Link from "next/link";
import React from "react";
import { useRouter } from "next/router";
import Image from 'next/image';
import { useQuery, gql, ApolloConsumer } from '@apollo/client';

export default function Sidebar() {

    // routing de next
    const router = useRouter();
    //console.log(router.pathname);    

    return (
        <aside className=" md:w-1/5 lg:w-1/5 xl:w-1/5 sm:min-h-screen p-5 rounded-md" style={{ backgroundColor: "hsla(0, 0%, 100%, 0.9)", backdropFilter: "saturate(200%) blur(25px)" }}> {/*bg-gray-800 sm:w-1/3 xl:w-1/5 sm:min-h-screen p-5*/}

            {/*sm:w-1/5 arriba*/}
            {/*<p className="text-white text-2xl font-black">Gestión Prácticas-Empresas</p>*/}
            <div className='text-center'>
                <Image
                    src="/logo.png"
                    alt="Landscape picture"
                    width={200}
                    height={200}
                    priority
                />
                <h1 className="text-lg md:text-2xl lg:text-2xl xl:text-3xl font-bold tracking-tight mb-12 " style={{ color: "hsl(217, 79%, 78%)" }}>IES Al-Ándalus</h1>
            </div>

            <nav className="mt-5 list-none rounded bg-blue-200 font-light" >
                <li className={router.pathname === "/" ? "bg-blue-300 p-2 text-gray-100 text-lg" : "p-2 text-gray-800"}>
                    <div className="flex items-center">
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                            <path strokeLinecap="round" strokeLinejoin="round" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" />
                        </svg>
                        <Link href="/">
                            <a className="block pl-2">Inicio</a>
                        </Link>
                    </div>
                </li>
                <li className={router.pathname === "/empresa/empresas" || router.pathname === "/empresa/nuevaEmpresa" || router.pathname === "/empresa/editarEmpresa/[pid]" || router.pathname === "/empresa/informacionEmpresa/[pid]" || router.pathname === "/empleado/nuevoEmpleado/[pid]" ? "bg-blue-300 p-2 text-gray-100 text-lg" : "p-2 text-gray-800"}>
                    <div className="flex items-center">
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                            <path strokeLinecap="round" strokeLinejoin="round" d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4" />
                        </svg>
                        <Link href="/empresa/empresas">
                            <a className="block pl-2">Empresas</a>
                        </Link>
                    </div>
                </li>
                <li className={router.pathname === "/ciclos/ciclos" || router.pathname === "/ciclos/nuevoCiclo" || router.pathname === "/ciclos/editarCiclo/[pid]"? "bg-blue-300 p-2 text-gray-100 text-lg" : "p-2 text-gray-800"}>
                    <div className="flex items-center">
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                            <path strokeLinecap="round" strokeLinejoin="round" d="M4 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2V6zM14 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2V6zM4 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2v-2zM14 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2v-2z" />
                        </svg>
                        <Link href="/ciclos/ciclos">
                            <a className="block pl-2">Ciclos</a>
                        </Link>
                    </div>
                </li>
                <div className="divide-y divide-solid divide-white">
                        <div className="pt-2"></div>
                        <div className="pt-2"></div>
                    </div>
                <li className={router.pathname === "/configUsuario/configuracion" ? "bg-blue-300 p-2 text-gray-100 text-lg" : "p-2 text-gray-800"}>
                    <div className="flex items-center pt-2">
                        <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                            <path strokeLinecap="round" strokeLinejoin="round" d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z" />
                            <path strokeLinecap="round" strokeLinejoin="round" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                        </svg>
                        <Link href='/configUsuario/configuracion'                                                            >
                            <a className="block pl-2">Configuración</a>
                        </Link>
                    </div>
                </li> 
            </nav>
        </aside>
    );
}