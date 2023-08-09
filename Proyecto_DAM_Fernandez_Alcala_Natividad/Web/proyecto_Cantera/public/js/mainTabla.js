//Código para Datables

//$('#example').DataTable(); //Para inicializar datatables de la manera más simple

$(function() {    
    $('#example').DataTable({
        responsive:true,
        pageLength : 5,
        lengthMenu: [[5, 10, 20, -1], [5, 10, 20, 'Todos']],
    //para cambiar el lenguaje a español
        "language": {
                "lengthMenu": "Mostrar _MENU_ registros",
                "zeroRecords": "No se encontraron resultados",
                "info": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                "infoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                "infoFiltered": "(filtrado de un total de _MAX_ registros)",
                "sSearch": "Buscar:",
                "oPaginate": {
                    "sFirst": "Primero",
                    "sLast":"Último",
                    "sNext":"Siguiente",
                    "sPrevious": "Anterior"
			     },
			     "sProcessing":"Procesando...",
            },

        /* dom: 'lBfrtip', */
        dom: 
        "<'row'<'col-md-5'l><'col-md-2'B><'col-md-5'f>>"+
        "<'row'<'col-md-12'rt>>"+
        "<'row'<'col-md-5'i><'col-md-7'p>>",
        /* buttons: [
            'excel', 'pdf', 'print'
        ] */
        /* buttons: [
            'copy', 'csv', 'excel', 'pdf', 'print'
        ] */
        buttons:[ 
			{
				extend:    'excelHtml5',
				text:      '<i class="fas fa-file-excel"></i> ',
				titleAttr: 'Exportar a Excel',
				className: 'btn btn-success'
			},
			{
				extend:    'pdfHtml5',
				text:      '<i class="fas fa-file-pdf"></i> ',
				titleAttr: 'Exportar a PDF',
				className: 'btn btn-danger'
			},
			{
				extend:    'print',
				text:      '<i class="fa fa-print"></i> ',
				titleAttr: 'Imprimir',
				className: 'btn btn-info'
			},
		]	   
    });     
});

$(function() {    
    $('#example2').DataTable({
        responsive:true,
        pageLength : 5,
        lengthMenu: [[5, 10, 20, -1], [5, 10, 20, 'Todos']],
    //para cambiar el lenguaje a español
        "language": {
                "lengthMenu": "Mostrar _MENU_ registros",
                "zeroRecords": "No se encontraron resultados",
                "info": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                "infoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                "infoFiltered": "(filtrado de un total de _MAX_ registros)",
                "sSearch": "Buscar:",
                "oPaginate": {
                    "sFirst": "Primero",
                    "sLast":"Último",
                    "sNext":"Siguiente",
                    "sPrevious": "Anterior"
			     },
			     "sProcessing":"Procesando...",
            },

        /* dom: 'lBfrtip', */
        dom: 
        "<'row'<'col-md-5'l><'col-md-2'B><'col-md-5'f>>"+
        "<'row'<'col-md-12'rt>>"+
        "<'row'<'col-md-5'i><'col-md-7'p>>",
        /* buttons: [
            'excel', 'pdf', 'print'
        ] */
        /* buttons: [
            'copy', 'csv', 'excel', 'pdf', 'print'
        ] */
        buttons:[ 
			{
				extend:    'excelHtml5',
				text:      '<i class="fas fa-file-excel"></i> ',
				titleAttr: 'Exportar a Excel',
				className: 'btn btn-success'
			},
			{
				extend:    'pdfHtml5',
				text:      '<i class="fas fa-file-pdf"></i> ',
				titleAttr: 'Exportar a PDF',
				className: 'btn btn-danger'
			},
			{
				extend:    'print',
				text:      '<i class="fa fa-print"></i> ',
				titleAttr: 'Imprimir',
				className: 'btn btn-info'
			},
		]	   
    });     
});

$(function() {    
    $('#examplePresencial').DataTable({
        responsive:true,
        pageLength : 5,
        lengthMenu: [[5, 10, 20, -1], [5, 10, 20, 'Todos']],
    //para cambiar el lenguaje a español
        "language": {
                "lengthMenu": "Mostrar _MENU_ registros",
                "zeroRecords": "No se encontraron resultados",
                "info": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                "infoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                "infoFiltered": "(filtrado de un total de _MAX_ registros)",
                "sSearch": "Buscar:",
                "oPaginate": {
                    "sFirst": "Primero",
                    "sLast":"Último",
                    "sNext":"Siguiente",
                    "sPrevious": "Anterior"
			     },
			     "sProcessing":"Procesando...",
            },

        /* dom: 'lBfrtip', */
        dom: 
        "<'row'<'col-md-5'l><'col-md-2'B><'col-md-5'f>>"+
        "<'row'<'col-md-12'rt>>"+
        "<'row'<'col-md-5'i><'col-md-7'p>>",
        /* buttons: [
            'excel', 'pdf', 'print'
        ] */
        /* buttons: [
            'copy', 'csv', 'excel', 'pdf', 'print'
        ] */
        buttons:[ 
			{
				extend:    'excelHtml5',
				text:      '<i class="fas fa-file-excel"></i> ',
				titleAttr: 'Exportar a Excel',
				className: 'btn btn-success'
			},
			{
				extend:    'pdfHtml5',
				text:      '<i class="fas fa-file-pdf"></i> ',
				titleAttr: 'Exportar a PDF',
				className: 'btn btn-danger'
			},
			{
				extend:    'print',
				text:      '<i class="fa fa-print"></i> ',
				titleAttr: 'Imprimir',
				className: 'btn btn-info'
			},
		],
        "order": [[ 0, "desc" ]]	   
    });     
});