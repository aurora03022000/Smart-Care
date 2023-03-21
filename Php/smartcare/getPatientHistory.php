<?php
    require_once "conn.php";
    require_once "validate.php";
    
    $sql = "SELECT * FROM patient ORDER BY patient_name ASC";
    $result = mysqli_query($conn, $sql);

    if($result){
        while($row=mysqli_fetch_assoc($result)){
            echo $row['patient_id'].'`'.$row['patient_name'].'`'.$row['gender'].'`'.$row['address'].'` '.$row['date_admit'].'`'.$row['date_dismiss'].'`';
        } 
     }
 
     else{
         echo 'failure';
     }
    
?>