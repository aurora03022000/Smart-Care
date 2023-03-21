<?php
if(isset($_POST['device_id']) ){
    require_once "conn.php";
    require_once "validate.php";
    
    $device_id = validate($_POST['device_id']);

    $sql = "SELECT * FROM deviceid WHERE device_id = '$device_id' and device_status='Available'";
    $result = mysqli_query($conn, $sql);
    $countRow = mysqli_num_rows($result);

    if($result){
        if($countRow > 0){
            echo 'success';
        }

        else{
            echo 'none';
        }
     }
 
     else{
         echo 'failure';
     }

}
?>