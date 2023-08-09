import Head from 'next/head'
import Layout from "../components/Layout";
import Image from 'next/image';
import Link from 'next/link';

export default function Index() {
  return (
    <div>
      <Layout>
        <div>
          <section className="">
            <div className=" py-12 md:px-12 text-gray-800 text-center lg:text-left">
              <div className="container mx-auto xl:px-32">
                <div className="grid lg:grid-cols-2 flex items-center">
                  <div className="md:mt-12 lg:mt-0 mb-12 lg:mb-0">
                    <div className="block rounded-lg shadow-lg px-6 py-12 md:px-12 lg:-mr-14 z-20" style={{ background: "hsla(0, 0%, 100%, 0.55)", backdropFilter: "blur(30px)" }}>
                      <h1 className="text-4xl md:text-5xl xl:text-6xl font-bold tracking-tight mb-12">Gestión Prácticas<br /><span className="text-blue-600">Formación Profesional</span></h1>
                        <Link href="/empresa/empresas">
                          <a className="inline-block px-7 py-3 mb-2 md:mb-0 md:mr-2 bg-blue-600 text-white font-medium text-sm leading-snug uppercase rounded shadow-md hover:bg-blue-700 hover:shadow-lg focus:bg-blue-700 focus:shadow-lg focus:outline-none focus:ring-0 active:bg-blue-800 active:shadow-lg transition duration-150 ease-in-out" data-mdb-ripple="true" data-mdb-ripple-color="light" href="#!" role="button">Empresas</a>
                        </Link>
                        <Link href="/ciclos/ciclos">
                          <a className="inline-block px-7 py-3 bg-transparent text-blue-600 font-medium text-sm leading-snug uppercase rounded hover:text-blue-700 hover:bg-gray-100 focus:bg-gray-100 focus:outline-none focus:ring-0 active:bg-gray-200 transition duration-150 ease-in-out" data-mdb-ripple="true" data-mdb-ripple-color="light" href="#!" role="button">Ciclos</a>
                        </Link>                      
                    </div>
                  </div>
                  <div className="md:mb-12 lg:mb-0">
                    <Image
                      src="/undraw.svg"
                      alt="Landscape picture"
                      width={500}
                      height={500}
                      layout="responsive"
                      className="z-10 shadow-lg"
                      priority
                    />
                  </div>
                </div>
              </div>
            </div>
          </section>

        </div>
      </Layout>
    </div>
  )
}
