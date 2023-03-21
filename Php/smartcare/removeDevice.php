<?php
if(isset($_POST['device_id'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $device_id = validate($_POST['device_id']);
    
    $checkIfUsed = "SELECT * FROM patient WHERE deviceID = '$device_id' and isExist=1";
    $checkIfUsedResult = mysqli_query($conn, $checkIfUsed);
    $checkIfUsedCount = mysqli_num_rows($checkIfUsedResult);

    if($checkIfUsedCount == 0){
        $sql1 = "DELETE FROM deviceid WHERE device_id='$device_id'";
        $result1 = $conn->query($sql1);

        if($result1){
            echo 'success';
        }

        else{
            echo 'failure';
        }
        
        
    }

    else if($checkIfUsedCount > 0){
        echo 'used';
    }

    else{
        echo 'failure';
    }

        

}
?>