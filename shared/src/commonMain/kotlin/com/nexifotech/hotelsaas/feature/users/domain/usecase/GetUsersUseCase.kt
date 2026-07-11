package com.nexifotech.hotelsaas.feature.users.domain.usecase

import com.nexifotech.hotelsaas.feature.users.domain.model.User
import com.nexifotech.hotelsaas.feature.users.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUsersUseCase(
    private val repository: UserRepository
) {
    operator fun invoke(searchQuery: String = "", roleFilter: String? = null, statusFilter: String? = null): Flow<List<User>> {
        return repository.getUsers().map { users ->
            var filteredList = users

            if (searchQuery.isNotBlank()) {
                filteredList = filteredList.filter {
                    it.name.contains(searchQuery, ignoreCase = true) ||
                    it.employeeId.contains(searchQuery, ignoreCase = true) ||
                    it.email.contains(searchQuery, ignoreCase = true)
                }
            }

            if (roleFilter != null && roleFilter != "All") {
                filteredList = filteredList.filter { it.role.displayName == roleFilter }
            }
            
            if (statusFilter != null && statusFilter != "All") {
                filteredList = filteredList.filter { it.status.displayName == statusFilter }
            }

            filteredList
        }
    }
}
