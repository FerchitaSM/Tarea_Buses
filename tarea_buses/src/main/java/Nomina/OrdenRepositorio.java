package Nomina;

import org.springframework.data.jpa.repository.JpaRepository;

interface OrderRepository extends JpaRepository<Orden, Long> {
}