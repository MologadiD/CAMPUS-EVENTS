@RestController
@RequestMapping("/api/register")
public class RegistrationController {
    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest request) {

    }
}
