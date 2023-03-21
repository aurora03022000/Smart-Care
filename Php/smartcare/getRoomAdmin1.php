<?php
    require_once "conn.php";
    require_once "validate.php";
    
    $sql = "SELECT * FROM room ORDER BY room_number ASC";
    $result = mysqli_query($conn, $sql);
    
    if($result){
       while($row=mysqli_fetch_assoc($result)){
            echo $row['room_number']."`";
        } 
    }

    else{
        echo 'failure';
    }
    
?>