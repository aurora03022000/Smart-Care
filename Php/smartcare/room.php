<?php
    require_once "conn.php";
    require_once "validate.php";
    
    $sql = "SELECT * FROM room";
    $result = mysqli_query($conn, $sql);
    
    while($row=mysqli_fetch_assoc($result)){
        echo $row['room_number']."`";
    }

?>