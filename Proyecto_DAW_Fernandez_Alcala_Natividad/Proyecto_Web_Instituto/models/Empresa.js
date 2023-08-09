const mongoose = require('mongoose');

const EmpresasSchema = mongoose.Schema({
    nombre:{
        type: String,
        required: true,
        trim: true
    },
    cif:{
       type: String,
       required: true,
       trim: true,
       unique: true 
    },
    representante:{
        type: String,
        required: true,
        trim: true 
    },
    nif:{
        type: String,
        required: true,
        trim: true ,
        //unique: true
    },
    telefono:{
        type: Number,
        required: true,
        trim: true 
    },
    direccion:{
        type: String,
        required: true,
        trim: true
    },
    ciclos: {
        type: Array       
    },
    creado: {
        type: Date,
        default: Date.now()
    }
});

module.exports = mongoose.model('Empresa', EmpresasSchema);