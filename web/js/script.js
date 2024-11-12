document.addEventListener("DOMContentLoaded", function () {
    loadPortfolio();
});

function loadPortfolio() {
    // Fetch portfolio data from the server
    fetch("PortfolioServlet")
        .then(response => response.json())
        .then(data => {
            const gallery = document.getElementById("portfolio-gallery");
            gallery.innerHTML = "";  // Clear any existing content

            // Check if any artworks are available
            if (data.length === 0) {
                gallery.innerHTML = "<p>No artwork available. Start uploading to showcase your portfolio!</p>";
                return;
            }

            // Create HTML elements for each artwork
            data.forEach(artwork => {
                const artworkDiv = document.createElement("div");
                artworkDiv.classList.add("artwork");

                // Artwork image
                const img = document.createElement("img");
                img.src = artwork.imageUrl;
                img.alt = artwork.title;
                artworkDiv.appendChild(img);

                // Artwork title and description
                const title = document.createElement("h3");
                title.textContent = artwork.title;
                artworkDiv.appendChild(title);

                const description = document.createElement("p");
                description.textContent = artwork.description;
                artworkDiv.appendChild(description);

                gallery.appendChild(artworkDiv);
            });
        })
        .catch(error => {
            console.error("Error loading portfolio:", error);
            const gallery = document.getElementById("portfolio-gallery");
            gallery.innerHTML = "<p>Failed to load portfolio. Please try again later.</p>";
        });
}
