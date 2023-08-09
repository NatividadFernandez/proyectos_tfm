/* $.ajaxSetup({
    headers: {
        'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
    }
}); */

document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    var estado = false;
    var mensage = "";
    var calendar = new FullCalendar.Calendar(calendarEl, {
    themeSystem: 'bootstrap',    
    titleFormat: {year: 'numeric', month: 'short'},
    selectable: true,
    editable:false,
    dayMaxEvents: 2,
    headerToolbar: {
        left: 'title',
        center: 'dayGridMonth timeGridWeek timeGridDay',
        right: 'prev next today' /*  Miboton */
    },
    buttonText: {
        today:    'Hoy',
        month:    'Mes',
        week:     'Semana',
        day:      'Dia'
    },
    customButtons: {
        Miboton: {
            text:"Añadir Evento",
            click: function (info) {
                var d = new Date();
                startD = "startD";
                limpiarFormulario(); 
                $('#start_date').val(convert(d, startD));
                $('#end_date').val(convert(d, startD));
                $("#exampleModal").modal('toggle');
            }
        }
    },
    dateClick:function (info) {      
        
        limpiarFormulario();        

        // Disable star date field
        $('#start_date').prop('disabled', true);

        // Setting form fields d teh selected date and time
        startD = "startD";
        startT = "startT";
        $('#start_date').val(convert(info.dateStr, startD));
        $('#start_time').val(convert(info.dateStr, startT));
        $('#end_date').val(convert(info.dateStr, startD));
        $('#end_time').val(convert(info.dateStr, startT));

        $('#btnAgregar').prop("disabled",false);
        $('#btnModificar').prop("disabled",true);
        $('#btnEliminar').prop("disabled",true);

        $('#exampleModal').modal();        
    },
    eventClick:function(info){   
        const d = new Date();
        startD = "startD";
        startT = "startT";      

        // Enable star date field
        $('#start_date').prop('disabled', false);

        $('#txtID').val(info.event.id);
        $('#txtTitulo').val(info.event.title);    
        $('#start_date').val(convert2(info.event.start,startD));
        $('#start_time').val(convert2(info.event.start,startT));
        
        /* alert(info.event.end); */
        if(info.event.end == null){
            $('#end_date').val(convert2(info.event.start,startD));
            $('#end_time').val(convert2(info.event.start,startT));
        } else {
            $('#end_date').val(convert2(info.event.end,startD));
            $('#end_time').val(convert2(info.event.end,startT));
        }
        
        $('#txtColor').val(info.event.backgroundColor);
        $('#txtDescripcion').val(info.event.extendedProps.descripcion);
        $('#checkPublico').prop("checked", info.event.extendedProps.publico);

        $('#btnAgregar').prop("disabled",true);
        $('#btnModificar').prop("disabled",false);
        $('#btnEliminar').prop("disabled",false);

        $("#exampleModal").modal();
    },
    select: function (arg) {

        limpiarFormulario(); 
        startD = "startD";
        startT = "startT";

        /* $('#start_date').prop('disabled', true); */

        $('#start_date').val(convert2(arg.start,startD));
        $('#start_time').val(convert2(arg.start,startT)); 
        $('#end_date').val(convert2(arg.end,startD));
        $('#end_time').val(convert2(arg.end,startT));

        $('#btnAgregar').prop("disabled",false);
        $('#btnModificar').prop("disabled",true);
        $('#btnEliminar').prop("disabled",true);

        $("#exampleModal").modal();
    },
    views: {
        dayGrid: {
          // options apply to dayGridMonth, dayGridWeek, and dayGridDay views
        },
        timeGrid: {
          // options apply to timeGridWeek and timeGridDay views
        },
        week: {
            titleFormat:{ year: 'numeric', month: 'short', day: 'numeric' },
            // options apply to dayGridWeek and timeGridWeek views
            dateClick:function (info) {        

                limpiarFormulario();  
            
                // Disable star date field
                $('#start_date').prop('disabled', true);
        
                // Setting form fields d teh selected date and time
                startD = "startD";
                startT = "startT"
                $('#start_date').val(convert2(info.dateStr, startD));
                $('#start_time').val(convert2(info.dateStr,startT));
                $('#end_date').val(convert2(info.dateStr, startD));
                $('#end_time').val(convert2(info.dateStr,startT)); 
        
                $('#btnAgregar').prop("disabled",false);
                $('#btnModificar').prop("disabled",true);
                $('#btnEliminar').prop("disabled",true);
        
                $('#exampleModal').modal();        
            }
        },
        day: {
            titleFormat:{ year: 'numeric', month: 'short', day: 'numeric' }, 
            dateClick:function (info) {        

                limpiarFormulario(); 

                // Disable star date field
                $('#start_date').prop('disabled', true);
        
                // Setting form fields d teh selected date and time
                startD = "startD";
                startT = "startT"
                $('#start_date').val(convert2(info.dateStr, startD));
                $('#start_time').val(convert2(info.dateStr,startT));
                $('#end_date').val(convert2(info.dateStr, startD));
                $('#end_time').val(convert2(info.dateStr,startT)); 
        
                $('#btnAgregar').prop("disabled",false);
                $('#btnModificar').prop("disabled",true);
                $('#btnEliminar').prop("disabled",true);
        
                $('#exampleModal').modal();        
            }
        }
    },
    
    events: url_show

    });
    calendar.setOption('locale','es');

    calendar.render();

    // Validate date and time    
    function validateDate(){
        if (($('#start_date').val() === $('#end_date').val()) && ($('#start_time').val() > $('#end_time').val())) {
            Swal.fire('La hora de finalización no puede ser menor que la hora de inicio.', '', 'info');
        } else if ($('#start_date').val() > $('#end_date').val()) {
            Swal.fire('La fecha de finalización no puede ser menor que la fecha de inicio.', '', 'info');
        } else {
            return true;
        }
    }

    function recolectarDatosGUI(method) {

        if (validateDate()) {
            nuevoEvento = {
                id: $('#txtID').val(),
                title: $('#txtTitulo').val(),
                descripcion: $('#txtDescripcion').val(),
                color: $('#txtColor').val(),
                textColor: '#FFFFFF',
                start: $('#start_date').val()+" "+$('#start_time').val(),
                end: $('#end_date').val()+" "+$('#end_time').val(),
                publico: $('#checkPublico').prop("checked") ? 1: 0,
                '_token':$("meta[name='csrf-token']").attr("content"),
                '_method':method
            }
            return (nuevoEvento);
        }         
    }

    function EnviarInformacion(accion,objEvento) {
        /*Develoteca*/
        $.ajax(
            {
            type:"POST",           
            url: url_ + accion,
            data: objEvento,
            success: function(msg){
                if(estado){
                    Swal.fire(mensage, '', 'success')
                    estado = false;
                    mensage = "";
                }
                $("#exampleModal").modal('toggle');
                calendar.refetchEvents();
            },
            error: function(){
                if( $('#txtTitulo').val() == "" || $('#txtDescripcion').val() == ""){
                    Swal.fire('Debes de rellenar los campos necesarios.', '', 'info');            
                } else {console.log("Se ha encontrado un error.")} 
            }
        });
    }

    $('#btnAgregar').click(function(){ 
        estado = true;
        mensage = "¡Evento creado correctamente!";
        ObjEvento = recolectarDatosGUI("POST");
        EnviarInformacion('',ObjEvento);        
    });

    $('#btnEliminar').click(function(){ 
        Swal.fire({
            title: '¿Eliminar Evento?',
            text: "El evento será eliminado.",
            icon: 'error',
            showCancelButton: true,
            confirmButtonText: 'Aceptar',
            cancelButtonText: 'Cancelar',
          }).then((result) => {
            /* Read more about isConfirmed, isDenied below */
            if (result.isConfirmed) {
                ObjEvento = recolectarDatosGUI("DELETE");
                EnviarInformacion('/' + $('#txtID').val() ,ObjEvento);
              Swal.fire('¡Evento eliminado correctamente!', '', 'success')
            }            
          })
    });

    $('#btnModificar').click(function(){
        Swal.fire({
            title: '¿Guardar cambios?',
            text: "El evento será eliminado",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Aceptar',
            cancelButtonText: 'Cancelar',
          }).then((result) => {
            /* Read more about isConfirmed, isDenied below */
            if (result.isConfirmed) {
                estado = true;
                mensage = "¡Evento modificado correctamente!";
                ObjEvento = recolectarDatosGUI("PATCH");
                EnviarInformacion('/' + $('#txtID').val() ,ObjEvento);
            } else if (/* result.isDenied */  result.isDismissed	) {
                Swal.fire('Los cambios no fueron guardados', '', 'info');
            }
          })
    });

    function limpiarFormulario() {
        $('#txtID').val("");
        $('#txtTitulo').val("");
        $('#start_date').val(""),
        $('#start_time').val(""),
        $('#end_date').val(""),
		$('#end_time').val(""),	
        $('#txtColor').val("");
        $('#txtDescripcion').val("");
        $('#checkPublico').prop('checked', false);
    }

    /* Obtenemos la fecha y la hora en la que estamos ahora*/ 
    function convert(str,valor) {
        
        var now = moment();        
        const d = new Date(str);
        let result;

        let month = '' + (d.getMonth() + 1);
        let day = '' + (d.getDate());
        let year = '' + (d.getFullYear());
        if(month.length < 2) month = '0' + month;
        if(day.length < 2) day = '0' + day;
        let hour = '' + now.hour();
        
        let minutes = '' + now.minutes();
        if(hour.length < 2) hour = '0' + hour;
        if(minutes.length < 2) minutes = '0' + minutes;
        
        switch (valor) {
            case startD:
                    result = [year,month,day].join('-');
                break;
            case startT:                
                    result = [hour,minutes].join(':');
                break;
        
            default:
                    result = null;
                break;
        }

        return result;
    }

    /* Obtenemos la fecha y la hora que nosotros elegimos */ 
    function convert2(str,valor) {

        const d = new Date(str);
        let result; 

        let month = '' + (d.getMonth() + 1);
        let day = '' + (d.getDate());
        let year = '' + (d.getFullYear());
        if(month.length < 2) month = '0' + month;
        if(day.length < 2) day = '0' + day;
        let hour = '' + d.getHours() ;
        let minutes = '' + d.getMinutes() ;
        if(hour.length < 2) hour = '0' + hour;
        if(minutes.length < 2) minutes = '0' + minutes;
        
        switch (valor) {
            case startD:
                    result = [year,month,day].join('-');
                break;
            case startT:                
                    result = [hour,minutes].join(':');
                break;
        
            default:
                result = null;
                break;
        }

        return result;
    }   
    
    $(".fc-header-toolbar").addClass("row");
    /* $(".fc-toolbar-chunk").addClass("col-xs-12 col-sm-12 col-md-4 col-xl-4 col-xl-4 px-0 py-1"); */
    $(".fc-toolbar-chunk").addClass("px-0 py-1");    
});