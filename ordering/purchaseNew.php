<?php

if($_SERVER["REQUEST_METHOD"]== "POST"){

    require 'connection.php';
    registerToken();
}

Function registerToken(){
    global $connect;

    $token = $_POST["token"];
    $product_id = $_POST["product_id"];
    
    
    $query = "INSERT INTO purchase(token,product_id) VALUES ('$token','$product_id');";

    if(mysqli_query($connect,$query)){
    	echo "Thanks For Purchase !! \n Have a Great Day";
    } 
    else {
    	echo "Something Wrong";
    }

    mysqli_close($connect);

}

?>
