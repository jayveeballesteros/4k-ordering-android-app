<?php

if($_SERVER["REQUEST_METHOD"]== "POST"){

    require 'connection.php';
    Buynow();
}

Function Buynow(){
    global $connect;

    $token = $_POST["token"];
    $product_id = $_POST["product_id"];
    $rating = $_POST["rating"];
    $comment = $_POST["comment"];

    $query = "INSERT INTO rating(token,product_id,rating,comment) VALUES ('$token','$product_id','$rating','$comment');";

    if(mysqli_query($connect,$query)) {

    	echo "Thank Your For Your Feedback";

    }  else {

    	echo "Something Went Wrong";
    }
    
}
mysqli_close($connect);
?>
