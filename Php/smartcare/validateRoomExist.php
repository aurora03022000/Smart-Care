<?php
if(isset($_POST['roomNumber'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $roomNumber = validate($_POST['roomNumber']);

    $sql = "SELECT * FROM room WHERE room_number='$roomNumber' and isExist=1";
    $result = $conn->query($sql);
    
    if($result->num_rows > 0){

            while($row=mysqli_fetch_assoc($result)){
                if($row['room_status'] == "Available"){
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