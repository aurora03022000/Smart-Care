<?php
if(isset($_POST['room_number'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $room_number = validate($_POST['room_number']);
    
    $checkIfUsed = "SELECT * FROM patient WHERE room = '$room_number' and isExist=1";
            $checkIfUsedResult = mysqli_query($conn, $checkIfUsed);
            $checkIfUsedCount = mysqli_num_rows($checkIfUsedResult);

            if($checkIfUsedCount == 0){
                $sql1 = "DELETE FROM room WHERE room_number='$room_number'";
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