package org.eusebiu.controller;

import org.eusebiu.dto.*;
import org.eusebiu.models.Booking;
import org.eusebiu.models.User;
import org.eusebiu.repository.VehicleRepository;
import org.eusebiu.security.JwtUtil;
import org.eusebiu.service.UserService;
import org.eusebiu.repository.BookingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil; // 1. Am adus Fabrica de Bratari!
    private final BookingRepository bookingRepository;
    private final VehicleRepository vehicleRepository;
    // 2. Am adaugat jwtUtil in constructor ca sa-l injecteze Spring Boot
    public UserController(UserService userService, JwtUtil jwtUtil,BookingRepository bookingRepository,VehicleRepository vehicleRepository) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.bookingRepository = bookingRepository;
        this.vehicleRepository = vehicleRepository;
    }

    //API 1 REGISTER
    //LINK: POST http://localhost:8080/api/users/register
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    //API 2 LOGIN
    //Link: POST http://localhost:8080/api/users/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Pasul A: Verificam userul si parola in Service (ca si pana acum)
            User user = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());

            // Pasul B: Parola e corecta! Hai sa printam Token-ul (Bratara)
            String token = jwtUtil.generateToken(user.getEmail());

            // Pasul C: Impachetam Token-ul, Username-ul si Email-ul in cutia noastra speciala
            LoginResponse response = new LoginResponse(token, user.getUsername(), user.getEmail());

            // Trimitem pachetul (Status 200 OK)
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            // Daca a aruncat eroare de parola sau email gresit din UserService, dam Status 400 Bad Request
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // API 3: GET PROFILE
    // Link: GET http://localhost:8080/api/users/profile
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Principal principal) {
        try {
            // "principal" este dosarul in care Paznicul (JwtFilter) a pus numele (emailul) dupa ce a validat token-ul
            if (principal == null) {
                return ResponseEntity.status(401).body("Nu esti autorizat. Lipseste Token-ul!");
            }

            // 1. Luam email-ul din dosar
            String email = principal.getName();

            // 2. Cautam userul in baza de date
            User user = userService.getUserByEmail(email);

            // 3. Il impachetam in DTO ca sa ascundem parola
            UserProfile profileDto = new UserProfile(user);

            // 4. Il trimitem frontend-ului!
            return ResponseEntity.ok(profileDto);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // API 4: UPDATE PROFILE
    // Link: PUT http://localhost:8080/api/users/profile
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(Principal principal, @RequestBody UpdateProfileRequest request) {
        try {
            // Verificam iarasi daca Paznicul a validat token-ul
            if (principal == null) {
                return ResponseEntity.status(401).body("Nu esti autorizat!");
            }

            // Luam emailul din dosarul cererii
            String email = principal.getName();

            // Facem update-ul folosind UserService
            User userModificat = userService.updateUserProfile(email, request);

            // Dupa ce s-a salvat, il impachetam la loc in DTO-ul de citire (UserProfileDto) si il returnam ca sa confirmam succesul
            UserProfile profileDto = new UserProfile(userModificat);
            return ResponseEntity.ok(profileDto);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // API 5: GET DASHBOARD SUMMARY
    // Link: GET http://localhost:8080/api/users/dashboard
    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboardSummary(Principal principal) {
        try {
            if (principal == null) {
                return ResponseEntity.status(401).body("Nu esti autorizat!");
            }

            String email = principal.getName();
            User user = userService.getUserByEmail(email);

            // --- MODIFICARE AICI: Înlocuim cifrele "de mână" cu interogări în DB ---

            // 1. Numărăm rezervările cu statusul 'Confirmed' din tabelul bookings
            int active = bookingRepository.countByUserIdAndStatus(user.getId(), "Confirmed");

            // 2. Numărăm toate rezervările acestui utilizator
            int total = bookingRepository.countByUserId(user.getId());

            // 3. Saved și Rating (momentan le lăsăm așa sau le poți adăuga în tabelul User ulterior)
            double saved = 0.0;
            double rating = 5.0;

            DashboardSummary dashboardStats = new DashboardSummary(
                    active,   // Din DB
                    total,    // Din DB
                    saved,
                    rating
            );

            return ResponseEntity.ok(dashboardStats);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // API 6: GET UPCOMING TRIPS
    // Link: GET http://localhost:8080/api/users/trips/upcoming
    @GetMapping("/trips/upcoming")
    public ResponseEntity<?> getUpcomingTrips(Principal principal) {
        try {
            if (principal == null) return ResponseEntity.status(401).body("Neautorizat");

            // 1. Aflam cine e userul
            User user = userService.getUserByEmail(principal.getName());

            // 2. Luam toate rezervarile lui din baza de date
            List<Booking> bookingsFromDb = bookingRepository.findByUserId(user.getId());

            // 3. Transformam fiecare Booking intr-un UpcomingTrip (DTO) pentru frontend
            List<UpcomingTrip> tripsForFrontend = new ArrayList<>();

            for (Booking b : bookingsFromDb) {
                // Cautam masina ca sa ii stim numele
                String carName = "Masina Necunoscuta";
                double pret = 0.0;

                var vehicle = vehicleRepository.findById(b.getVehicleId());
                if (vehicle.isPresent()) {
                    carName = vehicle.get().getBrand() + " " + vehicle.get().getModel();
                    pret = vehicle.get().getPricePerDay(); // Poti calcula si pretul total aici daca vrei
                }

                // Cream "randul" pentru tabel
                UpcomingTrip trip = new UpcomingTrip(
                        carName,
                        b.getStartDate(), // Data de inceput
                        b.getStatus(),
                        pret
                );
                tripsForFrontend.add(trip);
            }

            return ResponseEntity.ok(tripsForFrontend);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // API 8: GET SAVED PAYMENT METHODS
    // Link: GET http://localhost:8080/api/users/payment-methods
    // ==========================================
    @GetMapping("/payment-methods")
    public ResponseEntity<?> getPaymentMethods(Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body("Nu esti autorizat!");

        // Trimitem cardurile fix ca in design-ul frontend-ului
        List<PaymentMethod> cards = Arrays.asList(
                new PaymentMethod("Visa", "4242", "12/25", true),
                new PaymentMethod("Mastercard", "8888", "08/24", false)
        );
        return ResponseEntity.ok(cards);
    }

    // ==========================================
    // API 9: GET TRANSACTIONS HISTORY
    // Link: GET http://localhost:8080/api/users/transactions
    // ==========================================
    @GetMapping("/transactions")
    public ResponseEntity<?> getTransactions(Principal principal) {
        if (principal == null) return ResponseEntity.status(401).body("Nu esti autorizat!");

        // Trimitem o tranzactie de test
        List<Transaction> transactions = Arrays.asList(
                new Transaction("Mar 1, 2024", "Toyota Corolla rental", "Completed", 105.00)
        );
        return ResponseEntity.ok(transactions);
    }
    // API 10: ADD NEW PAYMENT METHOD
    // Link: POST http://localhost:8080/api/users/payment-methods
    // ==========================================
    @PostMapping("/payment-methods")
    public ResponseEntity<?> addPaymentMethod(Principal principal, @RequestBody AddCardRequest request) {
        if (principal == null) return ResponseEntity.status(401).body("Nu esti autorizat!");

        try {
            // Aici, in viata reala, am trimite cardul catre Stripe/banca.
            // Pentru proiectul nostru, vom simula ca l-am salvat cu succes.

            // 1. Luam doar ultimele 4 cifre din card pentru siguranta
            String nrCard = request.getCardNumber().replaceAll("\\s+", ""); // Scoatem spatiile
            String ultimele4 = nrCard.length() >= 4 ? nrCard.substring(nrCard.length() - 4) : "****";

            // 2. Determinam tipul cardului (o logica simpla de test: daca incepe cu 4 e Visa, cu 5 e Mastercard)
            String tipCard = nrCard.startsWith("4") ? "Visa" : "Mastercard";

            // 3. Pe viitor vom salva asta intr-un tabel in baza de date (ex: payment_methods).
            // Deocamdata construim obiectul de raspuns (DTO-ul pe care l-am creat la pasul anterior)
            PaymentMethod cardNou = new PaymentMethod(tipCard, ultimele4, request.getExpiryDate(), false);

            // Returnam frontend-ului cardul "salvat", ca sa stie sa isi actualizeze lista de pe ecran
            return ResponseEntity.ok(cardNou);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Eroare la adaugarea cardului: " + e.getMessage());
        }
    }
}