from flask import Flask, render_template, request, redirect, url_for, flash

app = Flask(__name__)
app.secret_key = "dev_key_for_testing"

# -------------------------
# Fake Data (In-Memory)
# -------------------------
products = [
    {
        "id": 1,
        "title": "Laptop",
        "description": "Powerful laptop for development",
        "price": 1200.00,
        "tags": ["electronics", "computer"],
        "in_stock": True
    },
    {
        "id": 2,
        "title": "Headphones",
        "description": "Noise cancelling headphones",
        "price": 250.00,
        "tags": ["audio"],
        "in_stock": False
    }
]

# -------------------------
# Global Context
# -------------------------
@app.context_processor
def inject_globals():
    return {
        "menu_items": [
            {"title": "Products"},
            {"title": "Add Product"}
        ],
        "theme": {
            "colors": [
                {"name": "primary", "value": "#007bff", "dark_value": "#0056b3"}
            ]
        },
        "alert_colors": {
            "success": "#28a745",
            "error": "#dc3545"
        },
        "show_header": True,
        "wide_layout": True,
        "columns": 3,
        "header_color": "#333",
        "header_size": "32px",
        "price_color": "#28a745",
        "dark_mode": False,
        "user": {
            "is_authenticated": True
        },
        "current_user": {
            "name": "Tester"
        }
    }

# -------------------------
# Routes
# -------------------------

@app.route("/")
def home():
    return redirect(url_for("list_products"))

# -------------------------
# Products List
# -------------------------
@app.route("/products")
def list_products():
    return render_template(
        "products.html",
        page_title="Products",
        items=products
    )

# -------------------------
# Product Details
# -------------------------
@app.route("/products/<int:product_id>")
def product_details(product_id):
    product = next((p for p in products if p["id"] == product_id), None)

    if not product:
        flash("Product not found", "error")
        return redirect(url_for("list_products"))

    return render_template(
        "product_details.html",
        page_title="Product Details",
        product=product
    )

# -------------------------
# Add Product
# -------------------------
@app.route("/products/add", methods=["GET", "POST"])
def add_product():
    if request.method == "POST":
        new_product = {
            "id": len(products) + 1,
            "title": request.form.get("name"),
            "description": request.form.get("description"),
            "price": float(request.form.get("price")),
            "tags": request.form.get("tags").split(","),
            "in_stock": "in_stock" in request.form
        }

        products.append(new_product)
        flash("Product added successfully", "success")
        return redirect(url_for("list_products"))

    return render_template(
        "add_product.html",
        page_title="Add Product"
    )

# -------------------------
# Run App
# -------------------------
if __name__ == "__main__":
    app.run(debug=True)
