var ctx = document.getElementById('myChart').getContext('2d');
var jd;
var myChart = new Chart(ctx, {
    type: 'doughnut',
    data: {
        labels: ['Red'],
        datasets: [{
                label: '# of Votes',
                data: jd,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)'
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)'
                ],
                borderWidth: 1
            }]
    },
    options: {
        scales: {
            yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
        }
    }
});