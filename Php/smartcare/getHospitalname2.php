<?php
    require_once "conn.php";
    require_once "validate.php";
    
    $sql = "SELECT * FROM hospital_information WHERE admin_id = '1'";
    $result = mysqli_query($conn, $sql);

    if($result){
        while($row=mysqli_fetch_assoc($result)){
            echo $row['hospital_name'];
        } 
     }
 
     else{
         echo 'failure';
     }
?>