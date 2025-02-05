// package main

// import (
// 	docs "employee-api/docs"
// 	"employee-api/routes"
// 	"github.com/gin-contrib/cors"
// 	"github.com/gin-gonic/gin"
// 	"github.com/penglongli/gin-metrics/ginmetrics"
// 	"github.com/sirupsen/logrus"
// 	swaggerfiles "github.com/swaggo/files"
// 	ginSwagger "github.com/swaggo/gin-swagger"
// )

// func init() {
// 	logrus.SetLevel(logrus.InfoLevel)
// 	logrus.SetFormatter(&logrus.JSONFormatter{}) // Set JSON formatter for logs
// }

// // @title Employee API
// // @version 1.0
// // @description The REST API documentation for employee webserver
// // @termsOfService http://swagger.io/terms/
// // @contact.name Opstree Solutions
// // @contact.url https://opstree.com
// // @contact.email opensource@opstree.com
// // @license.name Apache 2.0
// // @license.url http://www.apache.org/licenses/LICENSE-2.0.html
// // @BasePath /api/v1
// // @schemes http
// func main() {
// 	// Initialize router
// 	router := gin.Default()
// 	// Setup CORS to allow all origins
// 	router.Use(cors.New(cors.Config{
// 		AllowAllOrigins:  true, // This allows all origins
// 		AllowMethods:     []string{"GET", "POST", "PUT", "DELETE", "OPTIONS"},
// 		AllowHeaders:     []string{"Origin", "Content-Type", "Authorization"},
// 		ExposeHeaders:    []string{"Content-Length"},
// 		AllowCredentials: false,
// 	}))
// 	// Setup metrics monitoring
// 	monitor := ginmetrics.GetMonitor()
// 	monitor.SetMetricPath("/metrics")
// 	monitor.SetSlowTime(1)
// 	monitor.SetDuration([]float64{0.1, 0.3, 1.2, 5, 10})
// 	monitor.Use(router)
// 	// Setup routes
// 	v1 := router.Group("/api/v1")
// 	docs.SwaggerInfo.BasePath = "/api/v1/employee"
// 	routes.CreateRouterForEmployee(v1)
// 	// Swagger setup
// 	url := ginSwagger.URL("http://98.81.100.112:8082/swagger/doc.json")
// 	router.GET("/swagger/*any", ginSwagger.WrapHandler(swaggerfiles.Handler, url))
// 	// Start the server
// 	router.Run(":8082")
// }



package main
import (
        "time"
        docs "employee-api/docs"
        "github.com/gin-contrib/cors"
        "employee-api/middleware"
        "employee-api/routes"
        "github.com/gin-gonic/gin"
        "github.com/penglongli/gin-metrics/ginmetrics"
        "github.com/sirupsen/logrus"
        swaggerfiles "github.com/swaggo/files"
        ginSwagger "github.com/swaggo/gin-swagger"
)
var router = gin.New()
func init() {
        logrus.SetLevel(logrus.InfoLevel)
        logrus.SetFormatter(&logrus.JSONFormatter{}) // NEW
}
// @title Employee API
// @version 1.0
// @description The REST API documentation for employee webserver
// @termsOfService http://swagger.io/terms/
// @contact.name Opstree Solutions
// @contact.url https://opstree.com
// @contact.email opensource@opstree.com
// @license.name Apache 2.0
// @license.url http://www.apache.org/licenses/LICENSE-2.0.html
// @BasePath /api/v1
// @schemes http
func main() {
        //router := gin.Default()
        // CORS Middleware
        router.Use(cors.New(cors.Config{
                AllowOrigins:     []string{"http://18.143.178.99:3000"},
                AllowMethods:     []string{"GET", "POST", "PUT", "DELETE", "OPTIONS"},
                AllowHeaders:     []string{"Origin", "Content-Type", "Accept", "Authorization"},
                ExposeHeaders:    []string{"Content-Length"},
                AllowCredentials: true,
                MaxAge:           12 * time.Hour,
        }))
        // router.GET("/api/v1/employee/search/all", func(c *gin.Context) {
        //      c.JSON(200, gin.H{"message": "Success"})
        // })
        monitor := ginmetrics.GetMonitor()
        monitor.SetMetricPath("/metrics")
        monitor.SetSlowTime(1)
        monitor.SetDuration([]float64{0.1, 0.3, 1.2, 5, 10})
        monitor.Use(router)
        router.Use(gin.Recovery())                  // NEW
        router.Use(middlewares.LoggingMiddleware()) // NEW
        v1 := router.Group("/api/v1")
        docs.SwaggerInfo.BasePath = "/api/v1/employee"
        routes.CreateRouterForEmployee(v1)
        url := ginSwagger.URL("http://13.229.200.131:8081/swagger/doc.json")
        router.GET("/swagger/*any", ginSwagger.WrapHandler(swaggerfiles.Handler, url))
        router.Run(":8081")
