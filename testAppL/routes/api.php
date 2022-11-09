<?php

use App\Http\Controllers\login_controller;
use App\Http\Controllers\products_controller;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Route;

use function Ramsey\Uuid\v1;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});


Route::post('login',[login_controller::class, 'login']);

Route::get('products/list', [products_controller::class, 'index'])->middleware('auth:api');
Route::post('products/delete', [products_controller::class, 'delete'])->middleware('auth:api');
Route::post('products/save', [products_controller::class, 'save'])->middleware('auth:api');