   M S B A M L   `   `   `  ����E  ASmart Care, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null'clr-namespace:Smart_Care
Smart_Care  5   	        Smart_Care.RegisterPatient   5   	   \ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    5   	   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   *localclr-namespace:Smart_Care     5   	     � Title$  registerPatient��5   	   $��587.659��6!   $��426.805��62    � 
ResizeMode$ NoResize=�6B    � WindowStartupLocation$ CenterScreen=�6X   .��5	      � .+�5
   
   � $��50,-21,44.8,10.4q�6   $��Center=�6)   $��Center=�6D   $	��503��6a   .+�5      �� $��add_patient.png��6   $��Center=�6-   $��Center=�6J   $��44��6e   $��-139,-392,0,0q�6q   $��46��6�   $
��Fill=�6�   �� 5      $��Admit Patient��6   $��51,-385,0,0q�6,   $��Center=�6A   $��20(�6\   $��Century Gothic)�6j   $��Center=�6�   �� 5      $��Name :��6   $��Center=�6%   $��-277,-211,0,0q�6B   $��Center=�6Y   $��Century Gothic)�6t   $
��Bold"�6�   �� 5      $��Birthdate :��6   $��Center=�6*   $��-266,-71,0,0q�6G   $��Center=�6]   $��Century Gothic)�6x   $
��Bold"�6�   �� 5      $��Gender :��6   $��Center=�6'   $��-271,-139,0,0q�6D   $��Center=�6[   $��Century Gothic)�6v   $
��Bold"�6�   �� 5      $��	Address :��6   $��Center=�6(   $��-275,-4,0,0q�6E   $��Center=�6Z   $��Century Gothic)�6u   $
��Bold"�6�   �� 5      $��Number :��6   $��Center=�6'   $��-272,63,0,0q�6D   $��Center=�6Y   $��Century Gothic)�6t   $
��Bold"�6�   �� 5      $��	Disease :��6   $��Center=�6(   $��-275,136,0,0q�6E   $��Center=�6[   $��Century Gothic)�6v   $
��Bold"�6�   �� 5      $��Device ID :��6   $��Center=�6*   $��-263,202,0,0q�6G   $��Center=�6]   $��Century Gothic)�6x   $
��Bold"�6�   �� 5      $��Room :��6   $��Center=�6%   $��-282,273,0,0q�6B   $��Center=�6X   $��Century Gothic)�6s   $
��Bold"�6�   �� 5      $��	Encoder :��6   $��Center=�6(   $��-268,340,0,0q�6E   $��Center=�6[   $��Century Gothic)�6v   $
��Bold"�6�   �� 5      -   5         ��Name$ patientName��5      $��Center=�6+   $��28��6H   $��63,-210,0,0q�6T    �� TextWrapping$
 Wrap=�6i   $��Center=�6}   $	��250��6�   $��Century Gothic)�6�   $��3,5,0,0q�6�   4  .System.Windows.Automation.AutomationProperties   Name$
 name��6�   �� 5      -   5         ��Name$ genderPatient��5   A    � Cursor$
 Hand��6   $��Century Gothic)�6%   $��Center=�6X   $��Center=�6u   $��63,-141,0,0q�6�   $��28��6�   $	��250��6�   $��8,8,0,0q�6�   .@�5      �� .��6   Male6%   �� 5      .��6   Female6'   5      (  "System.Windows.Controls.DatePicker  5      -   5          Name$ bdate��5   5   $��Century Gothic)�6   $��Center=�6D   $��
62,-72,0,0q�6a   $��Center=�6u   $��28��6�   $	��250��6�   $��3,5,0,0q�6�   �� 5      -   5        $ address��5   2   $��Century Gothic)�6   $��Center=�6C   $��28��6`   $��	63,-3,0,0q�6l   $
 Wrap=�6   $��Center=�6�   $	��250��6�   $��3,5,0,0q�6�   �� 5      -   5        $ number��5   [   $��Century Gothic)�6   $��Center=�6k   $��28��6�   $��	62,64,0,0q�6�   $
 Wrap=�6�   $��Center=�6�   $	��250��6�   $��3,5,0,0q�6�   �� 5      -   5        $ patientDisease��5      $��Center=�6.   $��28��6K   $��
63,132,0,0q�6W   $
 Wrap=�6k   $��Center=�6   $	��250��6�   $��Century Gothic)�6�   $��3,5,0,0q�6�   �� 5      -   5        $ deviceid��5   A   $
 Hand��6   $��Century Gothic)�6%   $��Center=�6S   $��Center=�6p   $��
63,201,0,0q�6�   $��28��6�   $	��250��6�   $��8,8,0,0q�6�   5       �� 5!      -   5        $
 room��5!   A   $
 Hand��6   $��Century Gothic)�6%   $��Center=�6O   $��Center=�6l   $��
63,271,0,0q�6�   $��28��6�   $	��250��6�   $��8,8,0,0q�6�   5"      �� 5#      -	   5        $ encoder��5#   A   $
 Hand��6   $��Century Gothic)�6%   $��Center=�6R   $��Center=�6o   $��
63,339,0,0q�6�   $��28��6�   $	��250��6�   $��8,8,0,0q�6�   5$      �� 5%      -
   5        	 ��Name$	 admitPatient_Button��5%   g   $
 Hand��6   ���#0EB021
�������$��Century Gothic)�6K   $��Center=�6�   $��
41,458,0,0q�6�   $��Center=�6�   $	��173��6�   $��32��6�   
 � 	Resources
 5&       �� 
TargetType �� Property �� Value��%^   '��       �� 5'      " ��6   .�5(      ��  �@��CornerRadius$ 7��69   5)      5*      .��5+      ]� .+�5,      �� $��add_patient.png��6   $��21��65   $��24��6A   $��0,0,2,0q�6L   �� 5-      $��Admit Patient��6    $��Center=�65   5.      5/      �� 50      $��Patient Information��6   $��Italic$�62   $��Century Gothic)�6E   $��10(�6a   $��Center=�6o   $��Center=�6�   $��-224,-273,0,0q�6�   � 51      
�������$��1��6)   $��Center=�64   $	��320��6O   $��-1,-253,0,0q�6[   52      53      54      