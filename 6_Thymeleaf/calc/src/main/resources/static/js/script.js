document.addEventListener('DOMContentLoaded', function() {
    // Adding event listener for form submission
    document.getElementById("calculatorForm").addEventListener("submit", async function(event) {
        event.preventDefault();

        // Retrieve input values from the form
        const number1 = parseFloat(document.getElementById("number1").value);
        const number2 = parseFloat(document.getElementById("number2").value);
        const operation = document.getElementById("operation").value;

        // Get result container and message element
        const resultContainer = document.getElementById("resultContainer");
        const resultMessage = document.getElementById("resultMessage");

        // Clear previous result
        resultContainer.style.display = "none";
        resultMessage.textContent = "";

        try {
            // Send POST request to the backend with the input values
            const response = await fetch('/api/calculate', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    number1: number1,
                    number2: number2,
                    operation: operation
                })
            });

            // Parse the JSON response
            const result = await response.json();

            // Handle response based on success or error
            if (response.ok) {
                resultMessage.textContent = `Result: ${result.result}`;
            } else {
                resultMessage.textContent = `Error: ${result.operation}`;
            }

            // Display the result container
            resultContainer.style.display = "block";
        } catch (error) {
            // Handle errors that occur during the fetch operation
            resultMessage.textContent = "An error occurred. Please try again.";
            resultContainer.style.display = "block";
        }
    });
});
