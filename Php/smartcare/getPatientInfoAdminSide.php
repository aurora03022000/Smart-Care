<?php
    require_once "conn.php";
    require_once "validate.php";
    
    $sql = "SELECT * FROM patient WHERE isExist=1 ORDER BY patient_name ASC";
    $result = mysqli_query($conn, $sql);

    if($result){
        while($row=mysqli_fetch_assoc($result)){
            echo $row['patient_id'].'`'.$row['patient_name'].'`'.$row['gender'].'`'.$row['birthdate'].'`'.$row['address'].'`';
        } 
     }
 
     else{
         echo 'failure';
     }
    
?>