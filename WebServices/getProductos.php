<?php /*  * El siguiente código localiza un alumno  * AGZ    3/11/2016  */ 
 
$response = array(); 
 
$Cn = mysqli_connect("localhost","root","","inventario")or die ("server no encontrado"); 
mysqli_set_charset($Cn,"utf8"); 
 
// Checa que le este llegando por el método GET el nocontrol 
if ($_SERVER['REQUEST_METHOD']=='POST') {  

    $result = mysqli_query($Cn,"SELECT idprod,nomProd,existencia, precio from productos order by nomprod"); 
 
    if (!empty($result)) {         
        if (mysqli_num_rows($result) > 0) { 
            $producto = array(); 
            $response["success"] = 200;   
            $response["message"] = 'productos encontrados';   
            $response["productos"] = array(); 
            
            while($res = mysqli_fetch_array($result)){

                $producto["idprod"] = $res["idprod"];       
                $producto["nomProd"] = $res["nomProd"];       
                $producto["existencia"] = $res["existencia"];       
                $producto["precio"] = $res["precio"];
                array_push($response["productos"], $producto);

            }             
            
 
            // El success=10 es que encontro el producto          

 
           // codifica la información en formato de JSON response            
           echo json_encode($response);         } 
           else {             
            // No Encontro al alumno             
            $response["success"] = 404;  
            //No encontro información y el success = 0 indica no exitoso             
            $response["message"] = "Producto no encontrado";             
            echo json_encode($response);         }     } 
            else {         
                $response["success"] = 404;  //No encontro información y el success = 0 indica no exitoso         
                $response["message"] = "Producto no encontrado no encontrado";         
                echo json_encode($response);     } } 
                else {     
                    // required field is missing     
                    $response["success"] = 400;     
                    $response["message"] = "Faltan Datos de entrada"; 
    
                    echo json_encode($response); 
}
mysqli_close($Cn); 
?> 