package com.pms.cantera;

public class Urls {

    // URL Base
    public static final String ROOT_URL = "http://192.168.56.1/AndroidStudio/";

    // Login
    public static final String LOGIN_USER_URL = ROOT_URL+"validar_usuario.php";

    //Voladuras
    public static final String SHOW_ALL_VOLADURA_DATA_URL = ROOT_URL+"voladuras/voladuras.php";
    public static final String ADD_VOLADURA_URL = ROOT_URL+"voladuras/add_voladura.php";
    public static final String EDIT_VOLADURA_URL = ROOT_URL+"voladuras/editar_voladura.php";
    public static final String DELETE_VOLADURA_URL = ROOT_URL+"voladuras/borrar_voladura.php";


    // Productos
    public static final String SHOW_ALL_PRODUCTOS_DATA_URL = ROOT_URL+"productos/productos.php";
    public static final String ADD_PRODUCTO_URL = ROOT_URL+"productos/add_producto.php";
    public static final String EDIT_PRODUCTO_URL = ROOT_URL+"productos/editar_producto.php";
    public static final String DELETE_PRODUCTO_URL = ROOT_URL+"productos/borrar_producto.php";

    // Stock Productos
    public static final String SHOW_ALL_STOCK_PRODUCTOS_DATA_URL = ROOT_URL+"productos/stockproductos/stockproductos.php";
    public static final String ADD_STOCK_PRODUCTO_URL = ROOT_URL+"productos/stockproductos/add_stock_producto.php";
    public static final String EDIT_STOCK_PRODUCTO_URL = ROOT_URL+"productos/stockproductos/editar_stock_producto.php";
    public static final String DELETE_STOCK_PRODUCTO_URL = ROOT_URL+"productos/stockproductos/borrar_stock_producto.php";
    public static final String SHOW_LATEST_STOCK_PRODUCTOS_DATA_URL = ROOT_URL+"productos/stockproductos/ultimo_add_stock_producto.php";

    // Consumos
    public static final String SHOW_ALL_CONSUMOS_DATA_URL = ROOT_URL+"consumos/consumos.php";
    public static final String ADD_CONSUMO_URL = ROOT_URL+"consumos/add_consumo.php";
    public static final String EDIT_CONSUMO_URL = ROOT_URL+"consumos/editar_consumo.php";
    public static final String DELETE_CONSUMO_URL = ROOT_URL+"consumos/borrar_consumo.php";

    // Stock Consumos
    public static final String SHOW_ALL_STOCK_CONSUMOS_DATA_URL = ROOT_URL+"consumos/stockconsumos/stockconsumos.php";
    public static final String ADD_STOCK_CONSUMO_URL = ROOT_URL+"consumos/stockconsumos/add_stock_consumo.php";
    public static final String EDIT_STOCK_CONSUMO_URL = ROOT_URL+"consumos/stockconsumos/editar_stock_consumo.php";
    public static final String DELETE_STOCK_CONSUMO_URL = ROOT_URL+"consumos/stockconsumos/borrar_stock_consumo.php";
    public static final String SHOW_LATEST_STOCK_CONSUMOS_DATA_URL = ROOT_URL+"consumos/stockconsumos/ultimo_add_stock_consumo.php";

    // Registro Entradas y Salidas
    public static final String REGISTRO_DATA_URL = ROOT_URL+"registro/registro.php";
    public static final String ADD_REGISTRO_DATA_URL = ROOT_URL+"registro/registro_checkin.php";
    public static final String UPDATE_REGISTRO_DATA_URL = ROOT_URL+"registro/registro_checkout.php";

    // Configuracion
    public static final String LOAD_CONFIGURATION_DATA_URL = ROOT_URL+"configuracion/datos_usuario.php";


}
