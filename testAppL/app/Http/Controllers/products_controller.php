<?php

namespace App\Http\Controllers;

use App\Models\Product;
use Illuminate\Http\Request;

class products_controller extends Controller
{
    public function index()
    {
        $products = Product::all();
        return $products;
    }

    public function save(Request $request)
    {
        if($request->id == 0)
        {
            $product = new Product();
        }
        else{
            $product = Product::find($request->id);
        }

        $product->code = $request->code;
        $product->name = $request->name;
        $product->price = $request->price;
        $product->stock = $request->stock;

        $product->save();

        return $product;

    }

    public function delete(Request $request)
    {
        $product = Product::find($request->id);
        $product->delete();

        return "ok";
    }
}
