<?php
    require_once "conn.php";
    require_once "validate.php";
    
    $sql = "SELECT * FROM deviceid ORDER BY device_id ASC";
    $result = mysqli_query($conn, $sql);

    if($result){
        while($row=mysqli_fetch_assoc($result)){
            echo $row['device_id'].'`'.$row['device_status'].'`'.$row['ipAddress'].'`';
        } 
     }
 
     else{
         echo 'failure';
     }
    
?>