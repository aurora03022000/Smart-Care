<?php
    require_once "conn.php";
    require_once "validate.php";
    
    $sql = "SELECT * FROM deviceid WHERE device_status='Available'";
    $result = mysqli_query($conn, $sql);
    
    while($row=mysqli_fetch_assoc($result)){
        echo $row['device_id']."`";
    }

?>