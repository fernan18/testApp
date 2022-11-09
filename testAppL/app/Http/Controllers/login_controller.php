<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class login_controller extends Controller
{
    public function login(Request $request)
    {
        if(Auth::attempt(['email' => $request->email, 'password' => $request->password])){
            $user = Auth::user();
            //$success['token'] =  $user->createToken('MyApp')-> accessToken;
            //$success['name'] =  $user->name;
    
            $arr = array('acceso' => "Ok", 'error' => "", 'token' => $user->createToken('MyApp')-> accessToken);
    
            return json_encode($arr);
        }
        else{
    
            $arr = array('acceso' => "", 'error' => "No existe el usuario o contraseña", 'token' => "");
    
            return json_encode($arr);
        }
    }
}