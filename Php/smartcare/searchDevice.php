<?php
if(isset($_POST['searchDeviceCategory']) && isset($_POST['searchDeviceFilter'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $selectedValue = validate($_POST['searchDeviceCategory']);
    $searchValue = validate($_POST['searchDeviceFilter']);

    
    if($selectedValue == "Device ID"){
        $sql1 = "SELECT * FROM deviceid WHERE device_id='$searchValue' ORDER BY device_id ASC";
        $result1 = $conn->query($sql1); 
    }
    
    else if($selectedValue == "Status"){
        $sql1 = "SELECT * FROM deviceid WHERE device_status='$searchValue' ORDER BY device_id ASC";
        $result1 = $conn->query($sql1); 
    }

    else if($selectedValue == "IP Addresses"){
        $sql1 = "SELECT * FROM deviceid WHERE ipAddress = '$searchValue' ORDER BY device_id ASC";
        $result1 = $conn->query($sql1); 
    }

    if($result1){
        while($row = mysqli_fetch_assoc($result1)){
            echo $row['device_id'].'`'.$row['device_status'].'`'.$row['ipAddress'].'`';
        }
    }

    else{
        echo 'failure';
    }
    
}
?>