package com.example.challenge2025.ui.components.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.challenge2025.domain.model.user.User
import com.example.challenge2025.ui.components.assets.AvatarImage

@Composable
fun ProfileHeader(
    user: User,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
    ) {
        Box {
            // Banner
            Image(
                painter = painterResource(id = user.bannerRes),
                contentDescription = "Banner do perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            Box(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.BottomStart)
                    .offset(y = 40.dp)
            ) {
                // --- MODIFICAÇÃO APLICADA AQUI ---
                // Avatar
                AvatarImage(
                    user = user,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
                // ------------------------------------

                // Botão de Editar sobre o Avatar
                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(28.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(
                        Icons.Rounded.Edit,
                        contentDescription = "Editar Perfil",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // Informações do Usuário
        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(text = user.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text(text = user.status, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = user.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f))
            Spacer(modifier = Modifier.height(16.dp))

            // Tags
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                user.tags.forEach { tag ->
                    SuggestionChip(onClick = { /*TODO*/ }, label = { Text(tag) })
                }
            }
        }
    }
}