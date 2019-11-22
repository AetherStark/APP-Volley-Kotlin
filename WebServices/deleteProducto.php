<?php /*  * El siguiente código localiza un alumno  * AGZ    3/11/2016  */ 
 
$response = array(); 
 
$Cn = mysqli_connect("localhost","root","","inventario")or die ("server no encontrado"); 
mysqli_set_charset($Cn,"utf8"); 
 
// Checa que le este llegando por el método GET el nocontrol 
if ($_SERVER['REQUEST_METHOD']=='POST') {  
    $obj= json_decode(file_get_contents("php://input"),true);
    if(empty($obj)){
        $response["success"] = 400;  //No encontro información y el success = 0 indica no exitoso         
        $response["message"] = "fALTAN DATOS DE ENTRADA";         
        echo json_encode($response);    
    }else{

    
        $idprod = $obj['idprod']; 
    $result = mysqli_query($Cn,"DELETE FROM productos WHERE idProd= $idprod "); 
 
    if ($result) {         

            // El success=10 es que encontro el producto          
                    $response["success"] = 200;   
                    $response["message"] = 'producto Eliminado';   
                      
                     echo json_encode($response);         
    } else {             
            // No Encontro al alumno             
                    $response["success"] = 406;  
            //No encontro información y el success = 0 indica no exitoso             
                    $response["message"] = "Producto no Eliminado";             
                    echo json_encode($response);         
            }
        }     
    }
            else {         
                $response["success"] = 400;  //No encontro información y el success = 0 indica no exitoso         
                $response["message"] = "fALTAN DATOS DE ENTRADA";         
                echo json_encode($response);     
            }
            
mysqli_close($Cn); 
?> 