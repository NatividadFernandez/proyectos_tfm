const mongoose = require('mongoose');

const CiclosSchema = mongoose.Schema({    
    nombreCorto: {
        type: String,
        required: true,
        trim: true
    },
    nombreLargo: {
        type: String,
        required: true,
        trim: true
    },
    creado: {
        type: Date,
        default: Date.now()
    }
});

module.exports = mongoose.model('Ciclo', CiclosSchema);