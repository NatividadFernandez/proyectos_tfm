const {gql} = require('apollo-server');

// Schema
const typeDefs = gql`      

    type Usuario {
        id: ID   
        nombre: String
        apellido: String
        email : String
        password : String
        creado: String
    }

    type Token {
        token: String
    }

    type Empresa {
        id: ID
        nombre: String
        cif: String
        representante: String
        nif: String
        telefono: Int
        direccion: String
        ciclos: [EmpresaCiclo]
        creado: String
    }

    type Empleado {
        id: ID
        nombre: String
        apellido: String
        nif: String
        telefono: Int
        email: String
        empresa: ID        
        creado: String
    }

    type Ciclo {
        id: ID
        nombreCorto: String
        nombreLargo: String
        creado: String
    }

    type EmpresaCiclo {
        id: ID
        nombreCorto: String
        nombreLargo: String
    }

    input UsuarioInput {
        nombre: String!
        apellido: String!
        email: String!
        password: String!
    } 

    input AutenticarInput {
        email: String!
        password: String!
    }

    input EmpresaInput {
        nombre: String!
        cif: String!
        representante: String!
        nif: String!
        telefono: Int!        
        direccion: String!
        ciclos: [EmpresaCicloInput]
    }
    
    input EmpleadoInput {
        nombre: String!
        apellido: String!
        nif: String!
        telefono: Int!
        email: String!
        empresa: ID!
    }

    input EmpresaCicloInput {
        id: ID!
        nombreCorto: String!
        nombreLargo: String!
    }

    input CicloInput {
        nombreCorto: String!
        nombreLargo: String!
    }
    
    type Query {
        #Usuarios
        obtenerUsuario : Usuario

        #Empresas
        obtenerEmpresas : [Empresa]
        obtenerEmpresa(id: ID!) : Empresa
        obtenerEmpleadosEmpresa(id: ID!) : [Empleado]
        obtenerEmpresaCiclo(id: ID!) : [Empresa]

        #Ciclos
        obtenerCiclos : [Ciclo]
        obtenerCiclo(id: ID!) : Ciclo

    }

    type Mutation {
        # Usuarios
        nuevoUsuario(input: UsuarioInput) : Usuario
        autenticarUsuario(input: AutenticarInput) : Token
        actualizarUsuario(id: ID!, input: UsuarioInput) : Usuario

        # Empresas
        nuevaEmpresa(input: EmpresaInput) : Empresa 
        actualizarEmpresa(id: ID!, input: EmpresaInput) : Empresa   
        eliminarEmpresa(id: ID!) : String   

        #Empleados
        nuevoEmpleado(input: EmpleadoInput) : Empleado
        actualizarEmpleado(id: ID!, input: EmpleadoInput) : Empleado 
        eliminarEmpleado(id: ID!) : String

        #Ciclos
        nuevoCiclo(input: CicloInput) : Ciclo
        actualizarCiclo(id: ID!,  input: CicloInput) : Ciclo
        eliminarCiclo(id: ID!) : String
    }

`;

module.exports = typeDefs;