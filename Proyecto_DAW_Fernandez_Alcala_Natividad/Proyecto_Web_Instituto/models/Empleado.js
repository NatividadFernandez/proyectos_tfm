const mongoose = require('mongoose');

const EmpleadosSchema = mongoose.Schema({
    nombre:{
        type: String,
        required: true,
        trim: true
    },
    apellido:{
       type: String,
       required: true,
       trim: true 
    },
    nif:{
        type: String,
        required: true,
        trim: true ,
        unique: true
    },
    telefono:{
        type: Number,
        required: true,
        trim: true 
    },
    email:{
        type: String,
        required: true,
        trim: true,
        unique: true
    },
    empresa: {
        type: mongoose.Schema.Types.ObjectId,
        required: true
    },
    creado: {
        type: Date,
        default: Date.now()
    }
});

module.exports = mongoose.model('Empleado', EmpleadosSchema);