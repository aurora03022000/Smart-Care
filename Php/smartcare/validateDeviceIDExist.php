<?php
if(isset($_POST['deviceID'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $deviceID = validate($_POST['deviceID']);

    $sql = "SELECT * FROM deviceid WHERE device_id='$deviceID' and isExist=1";
    $result = $conn->query($sql);
    
    if($result->num_rows > 0){


            while($row=mysqli_fetch_assoc($result)){
                if($row['device_status'] == "Available"){
                    echo 'success';
                }

                else{
                    echo 'found';
                }
            }

    }

    else{
        echo 'failure';
    }

}
?>