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

    $idProd = $obj['idprod']; 
    $nomProd = $obj['nomProd']; 
    $existencia = $obj['existencia']; 
    $precio = $obj['precio']; 

    $result = mysqli_query($Cn,"UPDATE productos SET nomProd='$nomProd',existencia= $existencia ,precio=$precio WHERE idprod= $idProd"); 
 
    if ($result) {         

            // El success=10 es que encontro el producto          
                    $response["success"] = 200;   
                    $response["message"] = 'producto Actualizado';   
                      
                     echo json_encode($response);         
    } else {             
            // No Encontro al alumno             
                    $response["success"] = 406;  
            //No encontro información y el success = 0 indica no exitoso             
                    $response["message"] = "Producto no actualizado";             
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