const Usuario = require('../models/Usuario');
const Empresa = require('../models/Empresa');
const Empleado = require('../models/Empleado');
const Ciclo = require('../models/Ciclo');
const bcryptjs = require('bcryptjs');
const jwt = require('jsonwebtoken');
require('dotenv').config({ path: 'variables.env' });

const crearToken = (usuario, secreta, expiresIn) => {
    console.log(usuario);
    const { id, email, nombre, apellido } = usuario;
    return jwt.sign({ id, email, nombre, apellido }, secreta, { expiresIn })
}

const validarDNI = (dni) => {

    // obtenemos la letra accediendo al ultimo caracterde la cadena pasada (DNI)
    var letra_dni = dni.substr(-1);
    // obtenemos el numero
    var numero = dni.substring(0, dni.length - 1);

    // Operamos con el numero
    var resto = numero % 23;
    var letras = ['T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E', 'T']; 
    letras = letras.join("");

    var encontrado = letras.charAt(resto);

    // Si el dni es correcto devolvera true
    return letra_dni == encontrado;
}

// Resolvers
const resolvers = {
    Query: {
        obtenerUsuario: async (_, { }, ctx) => {
            /*const usuarioId = await jwt.verify(token, process.env.SECRETA);

            return usuarioId;*/

            return ctx.usuario;
        },
        obtenerEmpresas: async () => {
            try {
                const empresas = await Empresa.find({});
                return empresas;
            } catch (error) {
                console.log(error);
            }
        },
        obtenerEmpresa: async (_, { id }) => {
            // revisar si la empresa existe o no 
            const empresa = await Empresa.findById(id);

            if (!empresa) {
                throw new Error('Empresa no encontrada');
            }

            return empresa;
        },
        obtenerEmpresaCiclo: async (_, { id }) => {
            // revisar si el ciclo existe o no 
            const ciclo = await Ciclo.findById(id);

            if (!ciclo) {
                throw new Error('Ciclo no encontrado');
            }

            const empresas = await Empresa.find({});

            let arrayEmpresasCiclo = [];
            let cont = 0;

            empresas.filter(empresa => {
                console.log(empresa)
                empresa.ciclos.filter(ciclo => {
                    if (ciclo.id == id) {
                        console.log(ciclo)
                        arrayEmpresasCiclo[cont] = empresa;
                        cont += 1;
                    }

                })
            })

            console.log(arrayEmpresasCiclo);

            /*const empresa = Empresa.find(obj => {
                obj.ciclos.id == id
            })*/

            //console.log(empresa);

            return arrayEmpresasCiclo;
        },
        obtenerEmpleadosEmpresa: async (_, { id }) => {
            // revisar si la empresa existe o no 
            const empresa = await Empresa.findById(id);

            if (!empresa) {
                throw new Error('Empresa no encontrada');
            }
            console.log(empresa);
            try {
                const empleados = await Empleado.find({ empresa: id });
                //console.log(empleados);
                return empleados;
            } catch (error) {
                console.log(error);
            }
        },
        obtenerCiclos: async (_, { }) => {
            try {
                const ciclos = await Ciclo.find({});
                return ciclos;
            } catch (error) {
                console.log(error);
            }
        },
        obtenerCiclo: async (_, { id }) => {
            // revisar si el ciclo existe o no 
            const ciclo = await Ciclo.findById(id);

            if (!ciclo) {
                throw new Error('Ciclo no encontrado');
            }

            return ciclo;
        }

    },
    Mutation: {
        nuevoUsuario: async (_, { input }) => {

            const { email, password } = input;

            //Revisar si el usuario ya está registrado
            const existeUsuario = await Usuario.findOne({ email });

            if (existeUsuario) {
                throw new Error('El usuario ya está registrado');
            }

            // Hashear su password
            const salt = await bcryptjs.genSaltSync(10);
            input.password = await bcryptjs.hashSync(password, salt);

            //Guardarlo en la base de datos
            try {
                const usuario = new Usuario(input);
                usuario.save(); //guardarlo
                return usuario;
            } catch (error) {
                console.log(error)
            }
        },
        autenticarUsuario: async (_, { input }) => {

            const { email, password } = input;

            // Si el usurio existe
            const existeUsuario = await Usuario.findOne({ email });
            if (!existeUsuario) {
                throw new Error('El usuario no existe.');
            }

            //Revisar si el password es correcto
            const passwordCorrecto = await bcryptjs.compareSync(password, existeUsuario.password);
            if (!passwordCorrecto) {
                throw new Error("La contraseña es incorrecta");
            }

            //Crear el token
            return {
                token: crearToken(existeUsuario, process.env.SECRETA, '24h')
            }

        },
        actualizarUsuario: async (_, { id, input }) => {

            console.log(input);
            // revisar si la empresa existe o no 
            let usuario = await Usuario.findById(id);

            if (!usuario) {
                throw new Error('Usuario no encontrado');
            }

            const { email, password } = input

            // Buscamos el usuario que tenga el email que pasamos por input
            let usuarioEmailId = await Usuario.findOne({ email });

            // Un usuario con el email existe
            if (usuarioEmailId != null) {
                // Comprobamos que no solo seamos nosotros con ese email y 
                // lanzamos excepcion
                if (id != usuarioEmailId.id) {
                    throw new Error('Este email ya está registrado');
                }
            }

            /*console.log(input.password);
            console.log(usuario.password);*/

            if (password == '') {
                input.password = usuario.password;
            } else {
                // Hashear su password
                const salt = await bcryptjs.genSaltSync(10);
                input.password = await bcryptjs.hashSync(password, salt);
                console.log(input.password);
            }

            // guardarlo en la base de datos
            usuario = await Usuario.findOneAndUpdate({ _id: id }, input, { new: true });

            return usuario;

        },
        nuevaEmpresa: async (_, { input }) => {

            const { cif, nif } = input;

            if(!validarDNI(nif)){
                throw new Error('El nif no es correcto');
            }

            //Revisar si el usuario ya está registrado
            const existeEmpresa = await Empresa.findOne({ cif });

            const existeRepresentante = await Empresa.findOne({ nif });

            if (existeEmpresa) {
                throw new Error('La empresa ya está registrada');
            }

            if (existeRepresentante) {
                throw new Error('El nif ya está en uso');
            }

            try {
                const nuevaEmpresa = new Empresa(input);
                // almacenar en la bd
                const resultado = await nuevaEmpresa.save();

                return resultado;
            } catch (error) {
                console.log(error);
            }
        },
        actualizarEmpresa: async (_, { id, input }) => {
            // revisar si la empresa existe o no 
            let empresa = await Empresa.findById(id);

            const { nif , cif} = input;

            // Buscamos la empresa que tenga el nif/cif que pasamos por input
            let EmpresaNifId = await Empresa.findOne({ nif });
            let EmpresaCifId = await Empresa.findOne({ cif });

            // Un usuario con el nif existe
            if (EmpresaNifId != null) {
                // Comprobamos que no solo seamos nosotros con ese nif y 
                // lanzamos excepcion
                if (id != EmpresaNifId.id) {
                    throw new Error('Este nif ya está registrado');
                }
            }

            // Un usuario con el cif existe
            if (EmpresaCifId != null) {
                // Comprobamos que no solo seamos nosotros con ese cif y 
                // lanzamos excepcion
                if (id != EmpresaCifId.id) {
                    throw new Error('Este cif ya está registrado');
                }
            }

            if(!validarDNI(nif)){
                throw new Error('El nif no es correcto');
            }

            if (!empresa) {
                throw new Error('Empresa no encontrada');
            }

            // guardarlo en la base de datos
            empresa = await Empresa.findOneAndUpdate({ _id: id }, input, { new: true });

            return empresa;

        },
        eliminarEmpresa: async (_, { id }) => {
            // revisar si la empresa existe o no 
            let empresa = await Empresa.findById(id);            

            if (!empresa) {
                throw new Error('Empresa no encontrada');
            }

            // Buscamos los empleados que pertenecen a esta empresa
            const empleados = await Empleado.find({});
            const empleadosEmpresa = empleados.filter(empleado => empleado.empresa == id);

            // Eliminamos todos los empleados que pertenecen a esa empresa
            if(empleadosEmpresa != null){
                empleadosEmpresa.map( async empleado => {
                    console.log(empleado.id)
                    await Empleado.findOneAndDelete({ _id: empleado.id });
                })
            }          

            //console.log('AQUI');
            //Eliminar
            await Empresa.findOneAndDelete({ _id: id });

            return "Empresa Eliminada";
        },
        nuevoEmpleado: async (_, { input }) => {

            const { email, empresa, nif } = input

            // revisar si la empresa existe o no 
            let empresaId = await Empresa.findById(empresa);

            if (!empresaId) {
                throw new Error('Empresa no encontrada');
            }

            if(!validarDNI(nif)){
                throw new Error('El nif no es correcto');
            }

            let empleadoEmail = await Empleado.findOne({ email });
            let empleadoDNI = await Empleado.findOne({ nif });

            if (empleadoEmail) {
                throw new Error('El email ya existe');
            }

            if (empleadoEmail || empleadoDNI) {
                throw new Error('El nif ya existe');
            }

            try {
                const empleado = new Empleado(input);

                // almacenar en la bd
                const resultado = await empleado.save();

                return resultado;
            } catch (error) {
                console.log(error);
            }
        },
        actualizarEmpleado: async (_, { id, input }) => {
            // revisar si el empleado existe o no 
            let empleado = await Empleado.findById(id);  
            
            const { empresa, email, nif } = input;

            if (!empleado) {
                throw new Error('El empleado no existe');
            }

            // Al actualizar el empleado, la empresa no podrá cambiarse
            if (empleado.empresa != empresa) {
                throw new Error('La empresa no puede cambiar');
            }  
            
            if(!validarDNI(nif)){
                throw new Error('El nif no es correcto');
            }

            // Buscamos el usuario que tenga el email que pasamos por input
            let empleadoEmailId = await Empleado.findOne({ email });

            // Un usuario con el email existe
            if (empleadoEmailId != null) {
                // Comprobamos que no solo seamos nosotros con ese email y 
                // lanzamos excepcion
                if (id != empleadoEmailId.id) {
                    throw new Error('Este email ya está registrado');
                }
            }

            // Buscamos el usuario que tenga el nif/dni que pasamos por input
            let empleadoNifId = await Empleado.findOne({ nif });

            // Un usuario con el dni existe
            if (empleadoNifId != null) {
                // Comprobamos que no solo seamos nosotros con ese dni y 
                // lanzamos excepcion
                if (id != empleadoNifId.id) {
                    throw new Error('Este DNI ya está registrado');
                }
            }

            // guardarlo en la base de datos
            empleado = await Empleado.findOneAndUpdate({ _id: id }, input, { new: true });

            return empleado;
        },
        eliminarEmpleado: async (_, { id }) => {
            // revisar si el empleado existe o no 
            let empleado = await Empleado.findById(id);

            if (!empleado) {
                throw new Error('Empleado no encontrado');
            }

            //Eliminar
            await Empleado.findOneAndDelete({ _id: id });

            return "Empleado Eliminado";
        },
        nuevoCiclo: async (_, { input }) => {

            /// TENEMOS QUE PASAR LOS DATOS A LOWE O UPPER CASE PARA ASÍPODER COMPARAR
            const { nombreCorto, nombreLargo } = input;

            // revisar si los ciclos existen o no 
            let cicloIdnCorto = await Ciclo.findOne({ nombreCorto });
            let cicloIdnLargo = await Ciclo.findOne({ nombreLargo });

            if (cicloIdnCorto || cicloIdnLargo) {
                throw new Error('Este ciclo ya existe.');
            }

            try {
                const ciclo = new Ciclo(input);

                // almacenar en la bd
                const resultado = await ciclo.save();

                return resultado;
            } catch (error) {
                console.log(error);
            }
        },
        actualizarCiclo: async (_, { id, input }) => {
            // revisar si el empleado existe o no 
            let ciclo = await Ciclo.findById(id);
            if (!ciclo) {
                throw new Error('Este ciclo no existe');
            }

            const empresas = await Empresa.find({});
            //const empresasCiclo = empresas.filter(empresa => empresa.ciclo.filter(ciclo => ciclo.id == id) );
            /*empresas.map(empresa =>{
                empresa.ciclos.map( async ciclo => {
                    if(ciclo.id == id){
                        await Empresa.findOneAndUpdate({"_id": empresa.id, 'ciclos._id': ciclo.id}, {$set: {'ciclos.$.nombreCorto': input.nombreCorto, 'ciclos.$.nombreLargo': input.nombreLargo}}, { new: true })
                        .clone().catch(
                        function(error, result){
                            console.log(error);
                        })
                    }
                })
            })*/

            // guardarlo en la base de datos
            ciclo = await Ciclo.findOneAndUpdate({ _id: id }, input, { new: true });

            return ciclo;
        },
        eliminarCiclo: async (_, { id }) => {
            // revisar si la información de los ciclos existe o no 
            let ciclo = await Ciclo.findById(id);

            if (!ciclo) {
                throw new Error('Este ciclo no existe');
            }

            //Eliminar
            await Ciclo.findOneAndDelete({ _id: id });

            return "Ciclo eliminado.";
        }
    }
}

module.exports = resolvers;