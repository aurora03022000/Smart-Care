<?php
if(isset($_POST['device_id']) && isset($_POST['ipaddress'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $device_id = validate($_POST['device_id']);
    $ipaddress = validate($_POST['ipaddress']);

    $sql = "SELECT * FROM deviceid WHERE device_id = '$device_id'";
    $sqlResult = mysqli_query($conn,$sql);
    $sqlCount = mysqli_num_rows($sqlResult);

        if($sqlCount > 0){
            echo 'device id taken';
        }

        else if($sqlCount == 0){
            $sqlA = "SELECT * FROM deviceid WHERE ipAddress = '$ipaddress'";
            $sqlResultA = mysqli_query($conn, $sqlA);
            $sqlCountA = mysqli_num_rows($sqlResultA);

                if($sqlCountA > 0){
                    echo 'device ip taken';
                }
                
                else if($sqlCountA == 0){
                    $sql1 = "INSERT INTO deviceid(device_id, device_status, ipAddress) VALUES('$device_id', 'Available', '$ipaddress')";
                    $result1 = $conn->query($sql1);
            
                    if($result1){
                        echo "success";
                    }
            
                    else{
                        echo 'failure';
                    }
                }

            else{
                echo 'failure';
            }
            
        }

    else{
        echo 'failure';
    }

       

}
?>